package com.bettercalltolya.kevintask.ui.exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bettercalltolya.common.core.NoPendingExchangeException
import com.bettercalltolya.common.core.NoRatesAvailableException
import com.bettercalltolya.common.core.Result
import com.bettercalltolya.domain.currency.CurrencyRepository
import com.bettercalltolya.domain.currency.LastUsedCurrencyRepository
import com.bettercalltolya.domain.model.ExchangeHistoryItem
import com.bettercalltolya.domain.model.PendingExchange
import com.bettercalltolya.domain.usecases.ExecuteExchangeUseCase
import com.bettercalltolya.domain.usecases.GetExchangeRatesUseCase
import com.bettercalltolya.domain.usecases.ObserveBalancesUseCase
import com.bettercalltolya.kevintask.ui.core.dispatchers.CoroutineDispatchers
import com.bettercalltolya.kevintask.ui.core.extensions.toCurrencyString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeViewModel @Inject constructor(
    private val dispatchers: CoroutineDispatchers,
    private val executeExchangeUseCase: ExecuteExchangeUseCase,
    currencyRepository: CurrencyRepository,
    lastUsedCurrencyRepository: LastUsedCurrencyRepository,
    observeBalancesUseCase: ObserveBalancesUseCase,
    getExchangeRatesUseCase: GetExchangeRatesUseCase
) : ViewModel() {

    private var exchangeJob: Job? = null

    private val _exchangeNotifier = Channel<Result<ExchangeHistoryItem>>(CONFLATED)
    val exchangeNotifier: Flow<Result<ExchangeHistoryItem>> = _exchangeNotifier.receiveAsFlow()

    private val _exchangeState = MutableStateFlow(ExchangeState())
    val exchangeState: StateFlow<ExchangeState> = _exchangeState

    private val _currencies = MutableStateFlow(emptyList<String>())
    val currencies: StateFlow<List<String>> = _currencies

    private val _currencyFrom = MutableStateFlow("")
    val currencyFrom: StateFlow<String> = _currencyFrom

    private val _currencyTo = MutableStateFlow("")
    val currencyTo: StateFlow<String> = _currencyTo

    private val _sellAmount = MutableStateFlow(0.0)

    val balances = observeBalancesUseCase()
        .distinctUntilChanged()
        .shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    val availableBalance: SharedFlow<String> = combine(
        balances,
        _currencyFrom
    ) { balances, currency ->
        val available = balances
            .find { it.currency == currency }
            ?.amount
            ?: 0.0

        available.toCurrencyString()
    }.shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    fun setCurrencyFrom(currency: String) {
        _currencyFrom.tryEmit(currency)
    }

    fun setCurrencyTo(currency: String) {
        _currencyTo.tryEmit(currency)
    }

    fun setSellAmount(amount: Double) {
        _sellAmount.tryEmit(amount)
    }

    fun executePendingExchange() {
        exchangeJob?.cancel()
        exchangeJob = viewModelScope.launch(dispatchers.io) {
            val pending = _exchangeState.value.pendingExchange

            if (pending == null) {
                _exchangeNotifier.send(Result.error(NoPendingExchangeException()))
                return@launch
            }

            _exchangeNotifier.send(executeExchangeUseCase(pending))
        }
    }

    init {
        val currencies = currencyRepository.getAvailableCurrencies()
        val eurOrFirst = currencies.find { it == "EUR" } ?: currencies.first()
        val gbpOrLast = currencies.find { it == "GBP" } ?: currencies.last()

        val lastSold = lastUsedCurrencyRepository.getLastSoldCurrency(eurOrFirst)
        val lastBought = lastUsedCurrencyRepository.getLastBoughtCurrency(gbpOrLast)

        _currencies.tryEmit(currencies.toList())
        _currencyFrom.tryEmit(lastSold)
        _currencyTo.tryEmit(lastBought)

        viewModelScope.launch {
            _currencyFrom.filter { it.isNotBlank() }
                .flatMapLatest { fromCurrency ->
                    combine(
                        getExchangeRatesUseCase(fromCurrency),
                        _sellAmount,
                        _currencyTo
                    ) { ratesResult, amount, currencyTo -> Triple(ratesResult, amount, currencyTo) }
                }
                .onEach { (ratesResult, amount, currencyTo) ->
                    when (ratesResult) {
                        is Result.Loading -> {
                            _exchangeState.update { it.copy(loading = true, error = null) }
                        }
                        is Result.Error -> {
                            _exchangeState.update {
                                it.copy(
                                    loading = false,
                                    error = ratesResult.throwable,
                                    pendingExchange = null
                                )
                            }
                        }
                        is Result.Success -> {
                            _exchangeState.update { it.copy(loading = false, error = null) }

                            val rate = ratesResult.value
                            val targetRate = rate.rates[currencyTo]

                            if (targetRate == null) {
                                _exchangeState.update {
                                    it.copy(
                                        error = NoRatesAvailableException(),
                                        pendingExchange = null
                                    )
                                }
                                return@onEach
                            }

                            val exchange = PendingExchange(
                                sellAmount = amount,
                                sellCurrency = rate.base,
                                buyAmount = amount * targetRate,
                                buyCurrency = currencyTo
                            )

                            _exchangeState.update { it.copy(pendingExchange = exchange) }
                        }
                    }
                }
                .launchIn(this)
        }
    }
}
