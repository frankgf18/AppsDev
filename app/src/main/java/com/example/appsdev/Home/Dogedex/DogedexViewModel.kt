package com.example.appsdev.Home.Dogedex

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsdev.Home.Dogedex.Di.Api.ApiResponseStatus
import com.example.appsdev.Home.Dogedex.Di.Repository.DogRepository
import com.jjmf.dogedex.Model.Dog
import kotlinx.coroutines.launch

class DogedexViewModel : ViewModel() {
    private val repository = DogRepository()

    private val _dog = MutableLiveData<Dog>()
    val dog:LiveData<Dog> get() = _dog


    private val _status = MutableLiveData<ApiResponseStatus<Dog>>()
    val status:LiveData<ApiResponseStatus<Dog>> get() = _status

    fun getDogBymlId(mlDogId:String){
        viewModelScope.launch {
            handle(repository.getDogBymlId(mlDogId))
        }
    }

    private fun handle(api: ApiResponseStatus<Dog>){
        if (api is ApiResponseStatus.Succes){
            _dog.value = api.data!!
        }
        _status.value = api
    }

}