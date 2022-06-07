package com.example.appsdev.Home.Dogedex.Di.Api.Dto

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("id") val id:Long,
    @SerializedName("email") val email:String,
    @SerializedName("aunthentication_token") val aunthenticationToken: String
)
