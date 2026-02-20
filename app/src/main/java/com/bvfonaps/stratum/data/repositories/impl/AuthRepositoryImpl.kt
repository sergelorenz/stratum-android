package com.bvfonaps.stratum.data.repositories.impl

import com.bvfonaps.stratum.data.remote.api.ApiResult
import com.bvfonaps.stratum.data.remote.api.AuthApi
import com.bvfonaps.stratum.data.remote.api.dto.LoginDto
import com.bvfonaps.stratum.data.remote.api.dto.RegisterDto
import com.bvfonaps.stratum.data.remote.api.utils.ApiManager
import com.bvfonaps.stratum.data.repositories.interfaces.CheckAuthResult
import com.bvfonaps.stratum.data.repositories.interfaces.IAuthRepository
import okio.IOException
import retrofit2.HttpException


class AuthRepositoryImpl: IAuthRepository {
    private val apiRepository = ApiManager.apiRepository

    override suspend fun checkAuth(): CheckAuthResult {
        try {
            val authApi = apiRepository.createService(AuthApi::class.java)
            val response = authApi.me()
            return CheckAuthResult.INTACT_CONNECTION
        } catch (e: HttpException) {
            return when (e.code()) {
                401, 403 -> {
                    CheckAuthResult.EXPIRED
                } else -> {
                    CheckAuthResult.CONNECTION_NOT_FOUND
                }
            }
        } catch (e: IOException) {
            return CheckAuthResult.CONNECTION_NOT_FOUND
        }
    }

    override suspend fun login(
        username: String,
        password: String
    ): ApiResult<String?> {
        try {
            val authApi = apiRepository.createService(AuthApi::class.java)
            val loginDto = LoginDto(username, password)
            val response = authApi.login(loginDto)
            return ApiResult.Success(response.token)
        } catch (e: Exception) {
            return ApiResult.Error(e.message ?: "Unknown Error")
        }
    }

    override suspend fun register(
        username: String,
        password: String,
        confirmPassword: String
    ): ApiResult<String?> {
        try {
            val authApi = apiRepository.createService(AuthApi::class.java)
            val registerDto = RegisterDto(username, password, confirmPassword)
            val response = authApi.register(registerDto)
            return ApiResult.Success(response.token)
        } catch (e: Exception) {
            return ApiResult.Error(e.message ?: "Unknown Error")
        }
    }

    override suspend fun logout(): ApiResult<Unit> {
        try {
            val authApi = apiRepository.createService(AuthApi::class.java)
            val response = authApi.logout()
            return ApiResult.Success(Unit)
        } catch (e: Exception) {
            return ApiResult.Error(e.message ?: "Unknown Error")
        }
    }
}