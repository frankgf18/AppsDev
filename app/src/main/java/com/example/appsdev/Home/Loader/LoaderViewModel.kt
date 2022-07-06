package com.example.appsdev.Home.Loader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appsdev.Core.EstadosResult2
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class LoaderViewModel @Inject constructor(

) : ViewModel() {
    fun cargando() = liveData(Dispatchers.IO) {
        try {
            for (i in 0..1000){
                emit(EstadosResult2.Cargando(i*100/1000))
            }
            delay(5000)
            emit(EstadosResult2.Correcto("Entro"))
        }catch (ex:Exception){
            emit(EstadosResult2.Cargando(0))
            emit(EstadosResult2.Error(ex.message ?: ""))
        }finally {
            emit(EstadosResult2.Cargando(0))
        }
    }

}