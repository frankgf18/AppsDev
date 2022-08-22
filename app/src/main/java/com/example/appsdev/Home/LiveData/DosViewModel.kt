package com.example.appsdev.Home.LiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsdev.Home.LiveData.Data.UsuarioModel
import com.example.appsdev.Home.LiveData.Data.UsuarioModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DosViewModel @Inject constructor(
    private val repUsuarioModel: UsuarioModelRepository,
) : ViewModel() {
    fun insertar(us: UsuarioModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repUsuarioModel.insertar(us)
        }
    }
}