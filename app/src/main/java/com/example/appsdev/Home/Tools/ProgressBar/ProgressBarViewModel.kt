package com.example.appsdev.Home.Tools.ProgressBar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay

class ProgressBarViewModel : ViewModel() {
    fun loader() = liveData(Dispatchers.IO){
        emit(true)
        delay(5000)
        emit(false)
    }
}