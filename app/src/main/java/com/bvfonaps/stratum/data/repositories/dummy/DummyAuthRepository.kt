package com.bvfonaps.stratum.data.repositories.dummy

import com.bvfonaps.stratum.data.repositories.interfaces.CheckAuthResult
import com.bvfonaps.stratum.data.repositories.interfaces.IAuthRepository
import kotlinx.coroutines.delay

class TestAuthRepository: IAuthRepository {
    override suspend fun checkAuth(): CheckAuthResult {
        delay(1500)
        return CheckAuthResult.CONNECTION_NOT_FOUND
    }
}