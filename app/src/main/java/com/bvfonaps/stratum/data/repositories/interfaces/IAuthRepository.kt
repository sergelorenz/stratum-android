package com.bvfonaps.stratum.data.repositories.interfaces


enum class CheckAuthResult {
    CHECKING_CONNECTION,
    INTACT_CONNECTION,
    EXPIRED,
    CONNECTION_NOT_FOUND
}

interface IAuthRepository {

    suspend fun checkAuth(): CheckAuthResult
}