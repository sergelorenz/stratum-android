package com.bvfonaps.stratum.data.repositories.interfaces


interface IDiscoveryRepository {
    suspend fun discoverServer(): String?
}