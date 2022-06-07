package com.example.appsdev.Home.Dogedex.Di.Repository

import com.example.appsdev.Home.Dogedex.Di.Api.ApiResponseStatus
import com.example.appsdev.Home.Dogedex.Di.Api.DogsApi
import com.example.appsdev.Home.Dogedex.Di.Api.Dto.RegisterDTO
import com.example.appsdev.Home.Dogedex.Di.Api.Dto.UserDTOToMapper
import com.example.appsdev.Home.Dogedex.Di.Api.makeNetworkCall
import com.example.appsdev.Home.Dogedex.Model.User

class AuthRepository {
    suspend fun register(email:String, pass:String) : ApiResponseStatus<User> = makeNetworkCall {
        val registerDTO = RegisterDTO(email,pass,pass)
        val registerResponse = DogsApi.retrofitService.register(registerDTO)

        if (!registerResponse.is_success){
            throw Exception(registerResponse.message)
        }

        val userDTO = registerResponse.data.user
        val userToMapper = UserDTOToMapper()
        userToMapper.fromUserDTOToUserDomain(userDTO)
    }
}