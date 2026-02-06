package com.bvfonaps.stratum.data


import android.content.Context
import com.bvfonaps.stratum.data.discovery.DiscoveryRepository


interface AppContainer {
    val discoveryRepository : DiscoveryRepository
}


class DefaultAppContainer(private val context: Context) : AppContainer {
    override val discoveryRepository: DiscoveryRepository by lazy {
        DiscoveryRepository(context)
    }
}