package com.jjmf.dogedex.Model

import java.io.Serializable


data class Dog(
    val id: Long? = 0,
    val index: Int? = 0,
    val name:String? = "",
    val type:String? = "",
    val heightFemale:Double? = 0.0,
    val heightMale: Double? = 0.0,
    val imageUrl:String? = "",
    val lifeExpectancy:String? = "",
    val temperament:String? = "",
    val weigthFemale:String? = "",
    val weigthMale:String? = ""
) : Serializable
