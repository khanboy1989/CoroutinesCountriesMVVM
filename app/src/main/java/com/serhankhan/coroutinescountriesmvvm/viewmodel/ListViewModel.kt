package com.serhankhan.coroutinescountriesmvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.serhankhan.coroutinescountriesmvvm.model.CountriesService
import com.serhankhan.coroutinescountriesmvvm.model.Country
import kotlinx.coroutines.*

class ListViewModel: ViewModel() {

    val countriesService = CountriesService.getCountriesservice()
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler {coroutineContext, throwable ->  
        onError("Exception : ${throwable.localizedMessage}")
    }
    val countries = MutableLiveData<List<Country>>()
    val countryLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchCountries()
    }

    private fun fetchCountries() {
        loading.value = true

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = countriesService.getCountries()

            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    countries.value = response.body()
                    countryLoadError.value = null
                    loading.value = false
                }else {
                    onError("Error:${response.message()}")
                }
            }
        }

    }



    private fun onError(message: String) {
        countryLoadError.postValue(message)
        loading.postValue(false)
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}