package com.bvfonaps.stratum.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bvfonaps.stratum.data.remote.api.utils.ApiManager
import com.bvfonaps.stratum.data.remote.api.utils.AuthStateHolder
import com.bvfonaps.stratum.data.repositories.interfaces.CheckAuthResult
import com.bvfonaps.stratum.data.repositories.interfaces.IAuthRepository
import com.bvfonaps.stratum.data.repositories.interfaces.IDiscoveryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed interface DiscoveryState {
    object TestingConnection: DiscoveryState
    object Idle: DiscoveryState
    object Searching: DiscoveryState
    data class Found(val baseUrl: String): DiscoveryState
    object NotFound: DiscoveryState
}


class DiscoveryViewModel(
    private val authStateHolder: AuthStateHolder,
    private val discoveryRepository: IDiscoveryRepository,
    private val authRepository: IAuthRepository
): ViewModel() {

    private val apiRepository = ApiManager.apiRepository

    private val _discoveryState = MutableStateFlow<DiscoveryState>(
        DiscoveryState.TestingConnection
    )
    val discoveryState: StateFlow<DiscoveryState> = _discoveryState
    val showAuthState = authStateHolder.showAuthState

    init {
        checkAuth()
    }

    fun discover() {
        viewModelScope.launch {
            _discoveryState.value = DiscoveryState.Searching
            val result = discoveryRepository.discoverServer()
            if (result != null) {
                _discoveryState.value = DiscoveryState.Found(result)
                apiRepository.setBaseUrl(result)
                authStateHolder.openAuthDialog()
            } else {
                _discoveryState.value = DiscoveryState.NotFound
            }
        }
    }

    private fun checkAuth() {
        viewModelScope.launch {
            val authResult = authRepository.checkAuth()
            when (authResult) {
                CheckAuthResult.CONNECTION_NOT_FOUND -> {
                    _discoveryState.value = DiscoveryState.Idle
                }
                CheckAuthResult.INTACT_CONNECTION -> {

                }
                CheckAuthResult.EXPIRED -> {
                    _discoveryState.value = DiscoveryState.Found(apiRepository.getCurrentBaseUrl())
                    authStateHolder.openAuthDialog()
                }
                else -> {
                    _discoveryState.value = DiscoveryState.Idle
                }
            }
        }
    }

    fun closeAuthDialog() {
        authStateHolder.closeAuthDialog()
    }

    fun openAuthDialog() {
        authStateHolder.openAuthDialog()
    }
}