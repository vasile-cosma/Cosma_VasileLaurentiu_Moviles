package com.example.dogslist.api

import com.example.dogslist.api.services.DogApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
Retrofit para hacer peticiones a la API. Tal cual como vimos en clase, nada nuevo.
 */
class RetrofitApi {
    val retrofitBase = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService = retrofitBase.create(DogApiService::class.java)
}