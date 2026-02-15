package com.bvfonaps.stratum.di


import com.bvfonaps.stratum.data.repositories.interfaces.IApiRepository
import com.bvfonaps.stratum.data.repositories.interfaces.IDiscoveryRepository


interface AppContainer {
    val discoveryRepository : IDiscoveryRepository
    fun initApiManager()
}
