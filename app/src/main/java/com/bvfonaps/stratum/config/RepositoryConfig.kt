package com.bvfonaps.stratum.config

object RepositoryConfig {
    enum class RepositoryMode {
        PRODUCTION,
        DEVELOPMENT
    }

    var mode: RepositoryMode = RepositoryMode.DEVELOPMENT
}