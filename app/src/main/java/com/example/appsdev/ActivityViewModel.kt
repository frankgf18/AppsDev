package com.example.appsdev

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityViewModel : ViewModel(){
    //7000 -> Abrir camara
    //7001 -> Abrir camara
    var isCameraAllowed : MutableLiveData<Int> = MutableLiveData(0)
}