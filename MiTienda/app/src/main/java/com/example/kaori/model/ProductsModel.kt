package com.example.kaori.model

import com.example.kaori.api.RetrofitApi

class ProductsModel {

    private val retrofitApi = RetrofitApi()

    suspend fun getProducts(token: String): List<ProductDto> {
        val response = retrofitApi.retrofitService.getProducts("Bearer $token")

        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            emptyList()
        }
    }
}