package com.example.kaori.api.services

import com.example.kaori.api.requests.LoginRequest
import com.example.kaori.api.responses.LoginResponse
import com.example.kaori.model.ProductDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface KaoriApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @GET("products")
    suspend fun getProducts(@Header("Authorization")token: String) : Response<List<ProductDto>>



}