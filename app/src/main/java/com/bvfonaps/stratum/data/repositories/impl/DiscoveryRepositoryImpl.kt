package com.bvfonaps.stratum.data.repositories.impl

import com.bvfonaps.stratum.data.remote.api.AuthApi
import com.bvfonaps.stratum.data.repositories.interfaces.IApiRepository
import com.bvfonaps.stratum.data.repositories.interfaces.IDiscoveryRepository
import com.bvfonaps.stratum.ui.screens.splash.DiscoveryState
import retrofit2.HttpException


class DiscoveryRepositoryImpl: IDiscoveryRepository {
    override suspend fun discoverServer(): String? {
        return null
    }
}