package com.example.appsdev.Home.Dogedex.Di.Repository

import com.example.appsdev.Home.Dogedex.Di.Api.Dto.DogDTOMapper
import com.example.appsdev.Home.Dogedex.Di.Api.ApiResponseStatus
import com.example.appsdev.Home.Dogedex.Di.Api.DogsApi.retrofitService
import com.example.appsdev.Home.Dogedex.Di.Api.makeNetworkCall
import com.jjmf.dogedex.Model.Dog
import java.lang.Exception

class DogRepository {
    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogResponse = retrofitService.getAllDogs()
        val dogDTOList = dogResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }

    suspend fun getDogBymlId(mlDogId:String):ApiResponseStatus<Dog> = makeNetworkCall{
        val response = retrofitService.getDogBymlId(mlDogId)

        if (!response.is_success){
            throw Exception(response.message)
        }

        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOToDogDomain(response.data.dog)
    }
}