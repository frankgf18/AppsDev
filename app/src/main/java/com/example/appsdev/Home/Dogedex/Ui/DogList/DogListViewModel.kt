package com.example.appsdev.Home.Dogedex.Ui.DogList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appsdev.Home.Dogedex.Di.Api.ApiResponseStatus
import com.example.appsdev.Home.Dogedex.Di.Repository.DogRepository
import com.jjmf.dogedex.Model.Dog
import kotlinx.coroutines.launch

class DogListViewModel : ViewModel() {

    private val dogRepository = DogRepository()

    private val _dogList = MutableLiveData<List<Dog>>()
    val dogList : LiveData<List<Dog>> get()= _dogList


    private val _status = MutableLiveData<ApiResponseStatus<List<Dog>>>()
    val status : LiveData<ApiResponseStatus<List<Dog>>> get()= _status

    init {
        dowloadPerros()
    }

    private fun dowloadPerros() {
        viewModelScope.launch {
            try {
                _status.value = ApiResponseStatus.Loading()
                handleResponseStatur(dogRepository.downloadDogs())
            }catch (e:Exception){
            }
        }
    }

    private fun handleResponseStatur(api: ApiResponseStatus<List<Dog>>) {
        if (api is ApiResponseStatus.Succes){
            _dogList.value = api.data!!
        }
        _status.value = api
    }
}