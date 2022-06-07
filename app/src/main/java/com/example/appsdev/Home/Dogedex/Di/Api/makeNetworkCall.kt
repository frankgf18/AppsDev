package com.example.appsdev.Home.Dogedex.Di.Api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

suspend fun <T> makeNetworkCall(
    call: suspend () -> T
) : ApiResponseStatus<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResponseStatus.Succes(call())
        } catch (ex: UnknownHostException) {
            ApiResponseStatus.Error(ex.message.toString())
        } catch (e: Exception) {
            ApiResponseStatus.Error("Error desconocido - ${e.message}")
        }
    }
}