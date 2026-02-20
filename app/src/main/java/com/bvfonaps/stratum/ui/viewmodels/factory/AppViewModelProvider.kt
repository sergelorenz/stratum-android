package com.bvfonaps.stratum.ui.viewmodels.factory

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bvfonaps.stratum.StratumApplication
import com.bvfonaps.stratum.ui.screens.splash.AuthViewModel
import com.bvfonaps.stratum.ui.screens.splash.DiscoveryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            DiscoveryViewModel(
                authStateHolder = stratumApplication().container.authStateHolder,
                discoveryRepository = stratumApplication().container.discoveryRepository,
                authRepository = stratumApplication().container.authRepository
            )
        }
        initializer {
            AuthViewModel(
                authStateHolder = stratumApplication().container.authStateHolder,
                authRepository = stratumApplication().container.authRepository
            )
        }
    }
}


fun CreationExtras.stratumApplication(): StratumApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as StratumApplication)
