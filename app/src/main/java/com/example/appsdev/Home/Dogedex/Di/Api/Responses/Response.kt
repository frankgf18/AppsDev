package com.example.appsdev.Home.Dogedex.Di.Api.Responses

import com.example.appsdev.Home.Dogedex.Di.Api.Dto.DogDTO
import com.google.gson.annotations.SerializedName
import com.jjmf.dogedex.Model.Dog

data class DogListResponse(
    @SerializedName("message") val message: String,
    @SerializedName("is_success") val is_success: Boolean,
    @SerializedName("data") val data: DogListData
)

data class DogListData(
    @SerializedName("dogs") val dogs: List<DogDTO>
)

data class DogApiResponse(
    @SerializedName("message") val message: String,
    @SerializedName("is_success") val is_success: Boolean,
    @SerializedName("data") val data: DogData
)
data class DogData(
    @SerializedName("dog") val dog: DogDTO
)
