package com.bvfonaps.stratum.data.remote.api.utils

import com.bvfonaps.stratum.data.local.preferences.SessionManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TokenProvider(
    sessionManager: SessionManager
) {
    @Volatile
    private var token: String? = null

    init {
        CoroutineScope(Dispatchers.IO).launch {
            sessionManager.tokenFlow.collect {
                token = it
            }
        }
    }

    fun getToken(): String? = token
}