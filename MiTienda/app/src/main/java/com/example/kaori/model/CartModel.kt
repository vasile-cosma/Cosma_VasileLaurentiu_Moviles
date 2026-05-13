package com.example.kaori.model

import com.example.kaori.api.RetrofitApi
import com.example.kaori.api.requests.AddCartItemRequest
import com.example.kaori.api.responses.CartResponse

class CartModel {
    private val retrofitApi = RetrofitApi()

    suspend fun getCart(token: String): List<CartItemDto> {
        val response = retrofitApi.retrofitService.getCart("Bearer $token")

        return if (response.isSuccessful) {
            response.body()?.items ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun addProductToCart(
        token: String,
        productId: Int,
        units: Int
    ): Boolean {
        val request = AddCartItemRequest(
            productId = productId,
            units = units
        )
        val response = retrofitApi.retrofitService.addProductToCart(
            token = "Bearer $token",
            request= request
        )
        return response.isSuccessful
    }

    suspend fun removeProductFromCart(
        token: String,
        productId: Int
    ): Boolean {
        val response = retrofitApi.retrofitService.removeProductFromCart(
            token = "Bearer $token",
            productId = productId
        )
        return response.isSuccessful
    }

}