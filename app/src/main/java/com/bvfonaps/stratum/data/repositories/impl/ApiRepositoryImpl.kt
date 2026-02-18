package com.bvfonaps.stratum.data.repositories.impl

import com.bvfonaps.stratum.data.remote.api.utils.ApiService
import com.bvfonaps.stratum.data.repositories.interfaces.IApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ApiRepositoryImpl(
    private val apiService: ApiService
): IApiRepository {
    private val _baseUrl = MutableStateFlow("http://192.168.1.1:8080")
    val baseUrl: StateFlow<String> = _baseUrl.asStateFlow()

    override fun setBaseUrl(url: String) {
        _baseUrl.value = url
    }

    override fun getCurrentBaseUrl(): String = _baseUrl.value

    override fun <T> createService(serviceClass: Class<T>): T {
        return apiService.createService(_baseUrl.value, serviceClass)
    }
}