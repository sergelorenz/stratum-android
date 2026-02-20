package com.bvfonaps.stratum.di


import com.bvfonaps.stratum.data.remote.api.utils.AuthStateHolder
import com.bvfonaps.stratum.data.repositories.interfaces.IAuthRepository
import com.bvfonaps.stratum.data.repositories.interfaces.IDiscoveryRepository


interface AppContainer {
    val discoveryRepository : IDiscoveryRepository
    val authRepository: IAuthRepository

    val authStateHolder: AuthStateHolder

    fun initApiManager()
}
