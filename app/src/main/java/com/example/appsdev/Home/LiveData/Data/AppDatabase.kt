package com.example.appsdev.Home.LiveData.Data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UsuarioModel::class], version = 1, exportSchema = false)
abstract class AppDataBaseLiveData : RoomDatabase() {
    abstract fun usuarioModel(): UsuarioModelDao
}