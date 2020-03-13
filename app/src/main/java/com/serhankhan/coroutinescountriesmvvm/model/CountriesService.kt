package com.serhankhan.coroutinescountriesmvvm.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountriesService {
    private val BASE_URL = "https://raw.githubusercontent.com/"

    fun getCountriesservice():CountriesApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountriesApi::class.java)
    }
}