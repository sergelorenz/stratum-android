package com.bvfonaps.stratum.di


import com.bvfonaps.stratum.data.repositories.interfaces.IDiscoveryRepository


interface AppContainer {
    val discoveryRepository : IDiscoveryRepository
}
