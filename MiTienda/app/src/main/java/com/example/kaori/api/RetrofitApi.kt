package com.example.kaori.api

import com.example.kaori.api.services.KaoriApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
Retrofit para hacer peticiones a la API. Tal cual como vimos en clase, nada nuevo.
 */
class RetrofitApi {
    val retrofitBase = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8000/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService = retrofitBase.create(KaoriApiService::class.java)
}