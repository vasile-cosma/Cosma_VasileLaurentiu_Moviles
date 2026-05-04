package com.example.kaori.model

import android.util.Log
import com.example.kaori.api.RetrofitApi
import com.example.kaori.api.requests.LoginRequest
import com.example.kaori.api.responses.LoginResponse


class LoginModel {
    val retrofitApi = RetrofitApi()

    suspend fun login(
        username: String,
        password: String
    ) : LoginResponse {
        try {
            val loginRequest = LoginRequest(username = username, password = password)
            val response = retrofitApi.retrofitService.login(loginRequest)
            val loginResponse: LoginResponse

            if (response.isSuccessful && response.body() != null) {
                loginResponse = LoginResponse(response.body()!!.accessToken, response.body()!!.refreshToken)
            } else {
                loginResponse = LoginResponse(accessToken = null, null)
            }
            return loginResponse
        } catch (e: Exception) {
            Log.e("LoginError", "Error en login", e)
            return LoginResponse(accessToken = null, refreshToken = null)
        }
    }
}