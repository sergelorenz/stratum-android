package com.bvfonaps.stratum.data.repositories.interfaces

import com.bvfonaps.stratum.data.remote.api.dto.AuthResponseDto
import com.bvfonaps.stratum.data.remote.api.ApiResult


enum class CheckAuthResult {
    CHECKING_CONNECTION,
    INTACT_CONNECTION,
    EXPIRED,
    CONNECTION_NOT_FOUND
}


interface IAuthRepository {

    suspend fun checkAuth(): CheckAuthResult

    suspend fun login(username: String, password: String): ApiResult<String?>

    suspend fun register(username: String, password: String, confirmPassword: String): ApiResult<String?>

    suspend fun logout(): ApiResult<Unit>
}