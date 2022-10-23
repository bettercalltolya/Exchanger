package com.bettercalltolya.common.core

sealed class Result<out T> {
    data class Success<T>(val value: T) : Result<T>()
    object Loading : Result<Nothing>()
    data class Error(val throwable: Throwable) : Result<Nothing>()

    fun <R> map(transform: (T) -> R): Result<R> = when (this) {
        is Success -> of(transform(this.value))
        is Loading -> this
        is Error -> this
    }

    companion object {
        fun <T> of(value: T): Result<T> = Success(value)
        fun <T> loading(): Result<T> = Loading
        fun <T> error(t: Throwable): Result<T> = Error(t)
    }
}
