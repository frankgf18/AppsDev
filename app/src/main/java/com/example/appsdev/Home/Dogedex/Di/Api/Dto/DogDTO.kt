package com.example.appsdev.Home.Dogedex.Di.Api.Dto

import com.google.gson.annotations.SerializedName

data class DogDTO (
    @SerializedName("id") val id: Long,
    @SerializedName("index") val index: Int,
    @SerializedName("name_es") val name:String,
    @SerializedName("dog_type") val type:String,
    @SerializedName("height_female") val heightFemale:Double,
    @SerializedName("height_male") val heightMale: Double,
    @SerializedName("image_url") val imageUrl:String,
    @SerializedName("life_expectancy") val lifeExpectancy:String,
    @SerializedName("temperament") val temperament:String,
    @SerializedName("weight_female") val weigthFemale:String,
    @SerializedName("weight_male") val weigthMale:String
)