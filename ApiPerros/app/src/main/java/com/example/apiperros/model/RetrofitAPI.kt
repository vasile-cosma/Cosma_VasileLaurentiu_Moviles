package com.example.apiperros.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitAPI {
    val retrofitBase = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/breed/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService = retrofitBase.create(DogAPIService::class.java)
}