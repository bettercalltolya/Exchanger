package com.bettercalltolya.common.core

sealed class Optional<T> {
    data class Some<T> internal constructor(val value: T) : Optional<T>()
    open class None<T> internal constructor() : Optional<T>()
    private object NoneInstance : None<Any>()

    fun valueOr(value: T) = when (this) {
        is Some -> this.value
        is None -> value
    }

    fun valueOrNull(): T? = when (this) {
        is Some -> this.value
        is None -> null
    }

    fun valueOrThrow(): T = when (this) {
        is Some -> this.value
        is None -> throw IllegalStateException("Value is not present")
    }

    fun isPresent() = this is Some

    companion object {
        fun <T> of(value: T?): Optional<T> = value?.let { Some(value) } ?: none()

        @Suppress("UNCHECKED_CAST")
        fun <T> none(): None<T> = NoneInstance as None<T>
    }
}