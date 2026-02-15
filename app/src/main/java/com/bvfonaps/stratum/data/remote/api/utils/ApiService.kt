package com.bvfonaps.stratum.data.remote.api.utils

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService(
    authInterceptor: AuthInterceptor
) {
    private var retrofit: Retrofit? = null
    private var currentBaseUrl: String? = null

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    fun getRetrofit(baseUrl: String): Retrofit {
        if (retrofit == null || currentBaseUrl != baseUrl) {
            currentBaseUrl = baseUrl
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }

    fun <T> createService(baseUrl: String, serviceClass: Class<T>): T {
        return getRetrofit(baseUrl).create(serviceClass)
    }
}