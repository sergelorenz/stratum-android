package com.bvfonaps.stratum.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


class DiscoveryViewModel(
    private val discoveryRepository: IDiscoveryRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<DiscoveryState>(
        DiscoveryState.Idle
    )
    val uiState: StateFlow<DiscoveryState> = _uiState

    fun discover() {
        viewModelScope.launch {
            _uiState.value = DiscoveryState.Searching
            val result = discoveryRepository.discoverServer()
            _uiState.value = if (result != null) {
                DiscoveryState.Found(result)
            } else {
                DiscoveryState.NotFound
            }
        }
    }
}