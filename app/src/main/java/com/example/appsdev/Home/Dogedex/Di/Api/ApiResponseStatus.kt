package com.example.appsdev.Home.Dogedex.Di.Api

sealed class ApiResponseStatus<T> {
    class Loading<T>: ApiResponseStatus<T>()
    class Succes<T>(val data: T) : ApiResponseStatus<T>()
    class Error<T>(val message:String) : ApiResponseStatus<T>()
}