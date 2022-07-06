package com.example.appsdev.Core

sealed class EstadosResult<out T>{

    //LOADING
    data class Cargando(val bool: Boolean) : EstadosResult<Nothing>()

    //COMPLETE
    data class Correcto<T> (val datos:T? ) : EstadosResult<T>()

    //FAILURE
    data class Error(val mensajeError :String, val codigoError : Int? = null) : EstadosResult<Nothing>()
}

sealed class EstadosResult2<out T>{

    //LOADING
    data class Cargando(val bool: Int) : EstadosResult2<Nothing>()

    //COMPLETE
    data class Correcto<T> (val datos:T? ) : EstadosResult2<T>()

    //FAILURE
    data class Error(val mensajeError :String, val codigoError : Int? = null) : EstadosResult2<Nothing>()
}

