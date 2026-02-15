package com.bvfonaps.stratum.data.repositories.interfaces

interface IApiRepository {
    fun setBaseUrl(url: String)
    fun getCurrentBaseUrl(): String
    fun <T> createService(serviceClass: Class<T>): T
}