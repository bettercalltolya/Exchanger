package com.bettercalltolya.data.api.interceptor

import com.bettercalltolya.common.core.ApiException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ApiResponseErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        try {
            chain.proceed(chain.request())
                .also {
                    if (!it.isSuccessful) {
                        throw mapHttpError(it)
                    }
                }
        } catch (e: IOException) {
            when (e) {
                is SocketTimeoutException,
                is UnknownHostException -> throw ApiException.Network
                else -> throw e
            }
        }

    private fun mapHttpError(response: Response): Throwable {
        return when (response.code()) {
            401 -> ApiException.Unauthorized
            429 -> ApiException.ApiKeyLimitExceeded
            else -> ApiException.Unknown
        }
    }
}
