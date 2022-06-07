package com.example.appsdev.Home.Dogedex.Di.Api.Responses


import com.example.appsdev.Home.Dogedex.Di.Api.Dto.UserDTO
import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message") val message: String,
    @SerializedName("is_success") val is_success: Boolean,
    @SerializedName("data") val data: UserResponse
)

data class UserResponse(
    @SerializedName("user") val user: UserDTO
)
