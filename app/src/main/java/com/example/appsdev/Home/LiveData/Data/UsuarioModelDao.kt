package com.example.appsdev.Home.LiveData.Data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioModelDao {

    @Insert
    fun insertar(usuarioModel: UsuarioModel)

    @Query("select * from tb_usuarioModel")
    fun obtener() : LiveData<List<UsuarioModel>>

}