package com.example.kaori.api.services

import com.example.kaori.api.requests.LoginRequest
import com.example.kaori.api.responses.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface KaoriApiService {
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>
}