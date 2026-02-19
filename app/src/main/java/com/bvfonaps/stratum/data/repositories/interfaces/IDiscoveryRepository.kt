package com.bvfonaps.stratum.data.repositories.interfaces

import com.bvfonaps.stratum.data.remote.api.AuthApi
import com.bvfonaps.stratum.ui.screens.splash.DiscoveryState


interface IDiscoveryRepository {
    suspend fun discoverServer(): String?
}