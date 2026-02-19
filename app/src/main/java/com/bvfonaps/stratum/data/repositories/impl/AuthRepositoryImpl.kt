package com.bvfonaps.stratum.data.repositories.impl

import com.bvfonaps.stratum.data.remote.api.AuthApi
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
}