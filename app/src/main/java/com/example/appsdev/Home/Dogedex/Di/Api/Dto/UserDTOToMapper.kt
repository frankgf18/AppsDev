package com.example.appsdev.Home.Dogedex.Di.Api.Dto

import com.example.appsdev.Home.Dogedex.Model.User

class UserDTOToMapper {
    fun fromUserDTOToUserDomain(userDTO: UserDTO): User {
        return User(
            userDTO.id,
            userDTO.email,
            userDTO.aunthenticationToken
        )
    }

    fun fromUserDTOListToUserDomainList(dogDtoList: List<UserDTO>):List<User>{
        return dogDtoList.map { fromUserDTOToUserDomain(it) }
    }
}