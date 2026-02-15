package com.bvfonaps.stratum.data.remote.api.utils

import android.content.Context
import com.bvfonaps.stratum.data.local.preferences.SessionManager
import com.bvfonaps.stratum.data.repositories.impl.ApiRepositoryImpl
import com.bvfonaps.stratum.data.repositories.interfaces.IApiRepository


object ApiManager {
    lateinit var apiService: ApiService
    lateinit var apiRepository: IApiRepository

    fun init(context: Context) {
        val sessionManager = SessionManager(context)
        val tokenProvider = TokenProvider(sessionManager)
        val authInterceptor = AuthInterceptor(tokenProvider)
        apiService = ApiService(authInterceptor)
        apiRepository = ApiRepositoryImpl(apiService)
    }
}
