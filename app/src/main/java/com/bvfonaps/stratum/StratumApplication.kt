package com.bvfonaps.stratum

import android.app.Application
import com.bvfonaps.stratum.data.AppContainer
import com.bvfonaps.stratum.data.DefaultAppContainer

class StratumApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}
