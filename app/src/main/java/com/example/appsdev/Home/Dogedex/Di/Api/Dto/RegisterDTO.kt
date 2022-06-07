package com.example.appsdev.Home.Dogedex.Di.Api.Dto

import com.google.gson.annotations.SerializedName

data class RegisterDTO(
    @SerializedName("email") val email:String,
    @SerializedName("password") val password:String,
    @SerializedName("password_confirmation") val passConfirm:String
)
