package com.example.appsdev.Home.Dogedex.Di.Api.Responses

import com.example.appsdev.Home.Dogedex.Di.Api.Dto.DogDTO
import com.google.gson.annotations.SerializedName

data class DogListResponse(
    @SerializedName("message") val message: String,
    @SerializedName("is_success") val is_success: Boolean,
    @SerializedName("data") val data: DogListData
)

data class DogListData(
    @SerializedName("dogs") val dogs: List<DogDTO>
)