package com.example.kaori.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaori.api.responses.LoginResponse
import com.example.kaori.model.LoginModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class   LoginViewModel : ViewModel() {
    private val _loginState = MutableStateFlow<LoginResponse?>(null)
    val loginState = _loginState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            val response = LoginModel().login(username, password)
            _loginState.value = response
        }
    }
}