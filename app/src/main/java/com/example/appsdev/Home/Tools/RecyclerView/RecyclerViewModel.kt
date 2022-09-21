package com.example.appsdev.Home.Tools.RecyclerView

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsdev.Core.EstadosResult
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class RecyclerViewModel @Inject constructor() : ViewModel() {

    fun multiple(list: List<RecyclerView.Foto>, result:(EstadosResult<List<Uri>>)-> Unit){
        result.invoke(EstadosResult.Cargando)
        viewModelScope.launch (Dispatchers.IO){
            try {
                val fb = FirebaseStorage.getInstance().reference
                val folder = fb.child("LISTADO")
                val uri = list.map {img->
                        async {
                            folder.child(img.foto.lastPathSegment.toString()).putFile(img.foto)
                                .await()
                                .storage
                                .downloadUrl
                                .await()
                        }
                    }.awaitAll()
                result.invoke(EstadosResult.Correcto(uri))
            }catch (ex:Exception){
                result.invoke(EstadosResult.Error(ex.message ?: "Error 404"))
            }
        }
    }
}