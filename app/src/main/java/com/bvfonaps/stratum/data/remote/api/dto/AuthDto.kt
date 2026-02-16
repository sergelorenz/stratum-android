package com.bvfonaps.stratum.data.remote.api.dto

import com.google.gson.annotations.SerializedName

data class RegisterDto(
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("confirm_password")
    val confirmPassword: String
)


data class LoginDto (
    @SerializedName("username")
    val username: String,

    @SerializedName("password")
    val password: String
)


data class AuthResponseDto(
    @SerializedName("token")
    val token: String
)


data class MeResponseDto(
    @SerializedName("status")
    val isOk: Boolean
)
