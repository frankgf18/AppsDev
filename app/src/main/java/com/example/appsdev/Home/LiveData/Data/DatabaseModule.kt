package com.example.appsdev.Home.LiveData.Data

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModuleLiveData {

    @Singleton
    @Provides
    fun providesRoom(@ApplicationContext context: Context) : AppDataBaseLiveData {
        return Room.databaseBuilder(context.applicationContext, AppDataBaseLiveData::class.java, "BD_LiveData").build()
    }
}