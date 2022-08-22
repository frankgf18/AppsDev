package com.example.appsdev.Home.LiveData.Data

import androidx.lifecycle.LiveData
import javax.inject.Inject

interface UsuarioModelRepository {
    suspend fun insertar(us:UsuarioModel)
    suspend fun obtener() : LiveData<List<UsuarioModel>>
}

class UsuarioModelRepositoryImpl @Inject constructor(
    private val db : AppDataBaseLiveData
) : UsuarioModelRepository{
    override suspend fun insertar(us: UsuarioModel) {
        db.usuarioModel().insertar(us)
    }

    override suspend fun obtener(): LiveData<List<UsuarioModel>> {
        return db.usuarioModel().obtener()
    }

}