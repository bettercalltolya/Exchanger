package com.bettercalltolya.kevintask.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bettercalltolya.domain.model.ExchangeHistoryItem
import com.bettercalltolya.kevintask.R
import com.bettercalltolya.kevintask.databinding.ViewExchangeHistoryListItemBinding
import com.bettercalltolya.kevintask.ui.core.extensions.toCurrencyString
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class ExchangeHistoryAdapter :
    PagingDataAdapter<ExchangeHistoryItem, ExchangeHistoryAdapter.ViewHolder>(diffCallback) {

    private val dateFormatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ViewExchangeHistoryListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position) ?: return
        with(holder.binding) {
            fromTitle.text = item.sellAmount.toCurrencyString(item.sellCurrency)
            toTitle.text = item.buyAmount.toCurrencyString(item.buyCurrency)
            feesTitle.text = root.context.getString(
                R.string.exchange_history_with_fees_title_fmt,
                item.feesAmount.toCurrencyString(item.feesCurrency)
            )
            date.text = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(item.timestamp),
                ZoneId.systemDefault()
            ).format(dateFormatter)
        }
    }

    inner class ViewHolder(val binding: ViewExchangeHistoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<ExchangeHistoryItem>() {
            override fun areItemsTheSame(
                oldItem: ExchangeHistoryItem,
                newItem: ExchangeHistoryItem
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ExchangeHistoryItem,
                newItem: ExchangeHistoryItem
            ): Boolean = oldItem == newItem
        }
    }
}
