package com.bettercalltolya.common.core

import java.io.IOException

sealed class ApiException : IOException() {
    object Unauthorized : ApiException()
    object ApiKeyLimitExceeded : ApiException()
    object Network : ApiException()
    object Unknown : ApiException()
}

fun Throwable.isTerminatingException() =
    this is ApiException.Unauthorized || this is ApiException.ApiKeyLimitExceeded
