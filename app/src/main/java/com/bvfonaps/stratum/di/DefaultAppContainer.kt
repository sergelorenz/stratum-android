package com.bvfonaps.stratum.di

import android.content.Context
import com.bvfonaps.stratum.config.RepositoryConfig
import com.bvfonaps.stratum.data.remote.api.utils.ApiManager
import com.bvfonaps.stratum.data.repositories.dummy.TestDiscoveryRepository
import com.bvfonaps.stratum.data.repositories.interfaces.IDiscoveryRepository


class DefaultAppContainer(private val context: Context) : AppContainer {
    override val discoveryRepository: IDiscoveryRepository by lazy {
        when (RepositoryConfig.mode) {
            RepositoryConfig.RepositoryMode.DEVELOPMENT -> {
                TestDiscoveryRepository(context)
            }
            else -> {
                TestDiscoveryRepository(context)
            }
        }
    }

    override fun initApiManager() {
        ApiManager.init(context)
    }
}
