package com.example.recyclerview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.entities.MiColor
import com.example.recyclerview.model.ColoresModel
import com.example.recyclerview.model.Datos
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ColoresViewModel : ViewModel() {
    private var miModelo = ColoresModel()

    private var _datos = MutableStateFlow<Datos>(Datos("", mutableListOf<MiColor>()))

    val datos : StateFlow<Datos> get() = _datos

    fun retornarLista() {
        viewModelScope.launch {
            _datos.value = miModelo.retornarLista()
        }
    }
}