package com.example.calculadorav2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculadorav2.model.Datos
import com.example.calculadorav2.model.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private var _datos = MutableStateFlow<Datos>(Datos("", "","", "", false, false, ""))
    val datos: StateFlow<Datos> get() = _datos
    private var miEstado = MainState()

    fun anyadirTexto(texto: String){
        viewModelScope.launch {
            _datos.value = miEstado.anyadirTexto(texto)
            Log.d("mi tag:", _datos.value.historial)
        }
    }
    fun mostrarHistorial(texto: String) {
        viewModelScope.launch {
            _datos.value = miEstado.mostrarHistorial(texto)
        }
    }

    fun limpiar() {
        viewModelScope.launch {
            _datos.value = miEstado.limpiar()
        }
    }

    fun calcular(){
        viewModelScope.launch {
            _datos.value = miEstado.calcular()
        }
    }
}