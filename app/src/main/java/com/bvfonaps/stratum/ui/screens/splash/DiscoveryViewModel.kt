package com.bvfonaps.stratum.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bvfonaps.stratum.data.remote.api.utils.ApiManager
import com.bvfonaps.stratum.data.repositories.interfaces.IDiscoveryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed interface DiscoveryState {
    object Idle: DiscoveryState
    object Searching: DiscoveryState
    data class Found(val baseUrl: String): DiscoveryState
    object NotFound: DiscoveryState
}


sealed interface ShowAuthState {
    object Closed: ShowAuthState
    object Open: ShowAuthState
}


class DiscoveryViewModel(
    private val discoveryRepository: IDiscoveryRepository,
): ViewModel() {

    private val apiRepository = ApiManager.apiRepository

    private val _discoveryState = MutableStateFlow<DiscoveryState>(
        DiscoveryState.Idle
    )
    val discoveryState: StateFlow<DiscoveryState> = _discoveryState

    private val _showAuthState = MutableStateFlow<ShowAuthState>(
        ShowAuthState.Closed
    )
    val showAuthState: StateFlow<ShowAuthState> = _showAuthState

    fun discover() {
        viewModelScope.launch {
            _discoveryState.value = DiscoveryState.Searching
            val result = discoveryRepository.discoverServer()
            if (result != null) {
                _discoveryState.value = DiscoveryState.Found(result)
                apiRepository.setBaseUrl(result)
                _showAuthState.value = ShowAuthState.Open
            } else {
                _discoveryState.value = DiscoveryState.NotFound
            }
        }
    }

    fun closeAuthDialog() {
        _showAuthState.value = ShowAuthState.Closed
    }

    fun openAuthDialog() {
        _showAuthState.value = ShowAuthState.Open
    }
}