package com.example.calculadorav3.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calculadorav3.model.MyState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val myState = MyState()
    private val _data = MutableStateFlow<Double?>(0.0)
    val data : StateFlow<Double?> get() = _data

    fun add(num1: Double?, num2: Double?) {
        viewModelScope.launch {
            _data.value = myState.add(num1, num2)
        }
    }

    fun sustract(num1: Double?, num2: Double?) {
        viewModelScope.launch {
            _data.value = myState.sustract(num1, num2)
        }
    }

    fun multiply(num1: Double?, num2: Double?) {
        viewModelScope.launch {
            _data.value = myState.multiply(num1, num2)
        }
    }

    fun divide(num1: Double?, num2: Double?) {
        viewModelScope.launch {
            _data.value = myState.divide(num1, num2)
        }
    }
}