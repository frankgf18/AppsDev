package com.example.appsdev.Home.Dogedex.Di.Repository

import com.example.appsdev.Home.Dogedex.Di.Api.Dto.DogDTOMapper
import com.example.appsdev.Home.Dogedex.Di.Api.ApiResponseStatus
import com.example.appsdev.Home.Dogedex.Di.Api.DogsApi.retrofitService
import com.example.appsdev.Home.Dogedex.Di.Api.makeNetworkCall
import com.jjmf.dogedex.Model.Dog

class DogRepository {
    suspend fun downloadDogs(): ApiResponseStatus<List<Dog>> = makeNetworkCall {
        val dogResponse = retrofitService.getAllDogs()
        val dogDTOList = dogResponse.data.dogs
        val dogDTOMapper = DogDTOMapper()
        dogDTOMapper.fromDogDTOListToDogDomainList(dogDTOList)
    }
}