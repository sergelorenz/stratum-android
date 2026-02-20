package com.bvfonaps.stratum.data.repositories.dummy

import com.bvfonaps.stratum.data.remote.api.ApiResult
import com.bvfonaps.stratum.data.repositories.interfaces.CheckAuthResult
import com.bvfonaps.stratum.data.repositories.interfaces.IAuthRepository
import kotlinx.coroutines.delay

class TestAuthRepository: IAuthRepository {
    override suspend fun checkAuth(): CheckAuthResult {
        delay(1500)
        return CheckAuthResult.CONNECTION_NOT_FOUND
    }

    override suspend fun login(
        username: String,
        password: String
    ): ApiResult<String?> {
        delay(2000)
        return ApiResult.Success("This-is-a-sample-token?")
    }

    override suspend fun register(
        username: String,
        password: String,
        confirmPassword: String
    ): ApiResult<String?> {
        delay(2000)
        return ApiResult.Success("This-is-a-sample-token?")
    }

    override suspend fun logout(): ApiResult<Unit> {
        delay(2000)
        return ApiResult.Success(Unit)
    }
}