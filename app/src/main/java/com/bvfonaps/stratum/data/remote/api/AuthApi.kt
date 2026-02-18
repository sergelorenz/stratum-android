package com.bvfonaps.stratum.data.remote.api

import com.bvfonaps.stratum.data.remote.api.dto.AuthResponseDto
import com.bvfonaps.stratum.data.remote.api.dto.LoginDto
import com.bvfonaps.stratum.data.remote.api.dto.MeResponseDto
import com.bvfonaps.stratum.data.remote.api.dto.RegisterDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body user: RegisterDto): AuthResponseDto

    @POST("auth/login")
    suspend fun login(@Body user: LoginDto): AuthResponseDto

    @POST("auth/logout")
    suspend fun logout(): Response<Unit>

    @GET("auth/me")
    suspend fun me(): Response<MeResponseDto>
}