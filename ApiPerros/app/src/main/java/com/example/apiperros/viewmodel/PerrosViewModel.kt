package com.example.apiperros.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiperros.model.Datos
import com.example.apiperros.model.PerrosRespuesta
import com.example.apiperros.model.PerrosState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerrosViewModel : ViewModel() {
    val estado = PerrosState()
    private val _datos = MutableLiveData<Datos>(Datos(null.toString(), null, null, ArrayList() ))
    val datos : LiveData<Datos> get() = _datos

    fun recuperaFotosPaginacion(raza : String){
        viewModelScope.launch{
            _datos.value = estado.recuperaFotosPaginacion(raza)
        }
    }

    fun scrollFotos(){
        viewModelScope.launch{
            _datos.value = estado.scrollFotos()
        }
    }
}