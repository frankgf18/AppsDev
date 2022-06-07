package com.example.appsdev.Home.Dogedex.Di.Api

import com.example.appsdev.Core.Utils.BASE_URL
import com.example.appsdev.Home.Dogedex.Di.Api.Responses.DogListResponse
import com.example.appsdev.Home.Dogedex.Di.Api.Dto.RegisterDTO
import com.example.appsdev.Home.Dogedex.Di.Api.Responses.DogApiResponse
import com.example.appsdev.Home.Dogedex.Di.Api.Responses.RegisterResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface ApiService{

    @GET("dogs")
    suspend fun getAllDogs() : DogListResponse

    @POST("sign_up")
    suspend fun register(@Body registerDTO: RegisterDTO): RegisterResponse

    @GET("find_dog_by_ml_id")
    suspend fun getDogBymlId(@Query("ml_id") mlId: String): DogApiResponse
}

object DogsApi {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}