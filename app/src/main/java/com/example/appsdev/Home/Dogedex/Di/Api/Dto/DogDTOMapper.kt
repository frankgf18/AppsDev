package com.example.appsdev.Home.Dogedex.Di.Api.Dto

import com.jjmf.dogedex.Model.Dog

class DogDTOMapper {
    fun fromDogDTOToDogDomain(dogDTO: DogDTO): Dog {
        return Dog(
            dogDTO.id,
            dogDTO.index,
            dogDTO.name,
            dogDTO.type,
            dogDTO.heightFemale,
            dogDTO.heightMale,
            dogDTO.imageUrl,
            dogDTO.lifeExpectancy,
            dogDTO.temperament,
            dogDTO.weigthFemale,
            dogDTO.weigthMale
        )
    }

    fun fromDogDTOListToDogDomainList(dogDtoList: List<DogDTO>):List<Dog>{
        return dogDtoList.map { fromDogDTOToDogDomain(it) }
    }
}