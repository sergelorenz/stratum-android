package com.bvfonaps.stratum.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import com.bvfonaps.stratum.StratumApplication
import com.bvfonaps.stratum.data.discovery.DiscoveryRepository
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
    private val discoveryRepository: DiscoveryRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<DiscoveryState>(
        DiscoveryState.Idle
    )
    val uiState: StateFlow<DiscoveryState> = _uiState

    init {
        discover()
    }

    private fun discover() {
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

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as StratumApplication)
                val discoveryRepository = application.container.discoveryRepository
                DiscoveryViewModel(discoveryRepository = discoveryRepository)
            }
        }
    }
}