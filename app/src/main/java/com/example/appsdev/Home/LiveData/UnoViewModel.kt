package com.example.appsdev.Home.LiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.appsdev.Core.EstadosResult
import com.example.appsdev.Home.LiveData.Data.UsuarioModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class UnoViewModel @Inject constructor(
    private val repUsuarioModel: UsuarioModelRepository
) : ViewModel(){
    fun obtener() = liveData(Dispatchers.IO) {
        emit(EstadosResult.Cargando)
        try {
            emit(EstadosResult.Correcto(repUsuarioModel.obtener()))
        }catch (ex:Exception){
            emit(EstadosResult.Error(ex.message ?: "error al obtener"))
        }
    }
}