package com.example.appsdev.Home.Alertas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appsdev.Core.EstadosResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class AlertViewModel @Inject constructor(

) : ViewModel(){
    fun cargando() = liveData(Dispatchers.IO) {
        emit(EstadosResult.Cargando(true))
        try {
            delay(5000)
            emit(EstadosResult.Correcto("SI FUNCIONO"))
        }catch (ex:Exception){
            emit(EstadosResult.Error(ex.message.toString()))
        }finally {
            emit(EstadosResult.Cargando(false))
        }
    }


}