package com.bvfonaps.stratum.data.remote.api.utils

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AuthInterceptor(
    private val tokenProvider: TokenProvider
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenProvider.getToken()
        val newRequest =
            if (token != null) {
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            } else {
                chain.request()
            }

        return try {
            chain.proceed(newRequest)
        } catch (e: SocketTimeoutException) {
            throw IOException("Network timeout. Server unreachable.", e)
        } catch (e: ConnectException) {
            throw IOException("Cannot connect to server.", e)
        } catch (e: UnknownHostException) {
            throw IOException("No internet connection.", e)
        }
    }
}