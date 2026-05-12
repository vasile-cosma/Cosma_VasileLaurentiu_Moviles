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

            Log.d("***LOGIN RESPUESTA - ", "${response.code()}")

            if (response.isSuccessful) {
                loginResponse = LoginResponse(response.body()!!.accessToken, response.body()!!.refreshToken)
            } else {
                when (response.code()){
                    400,401,403 -> {
                        loginResponse = LoginResponse("errorUsuario", null)
                    }
                    else -> {
                        loginResponse = LoginResponse(null, null)
                    }
                }
            }
            return loginResponse
        } catch (e: Exception) {
            Log.e("LoginError", "Error en login", e)
            return LoginResponse(accessToken = null, refreshToken = null)
        }
    }
}