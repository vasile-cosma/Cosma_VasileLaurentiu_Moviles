package com.example.bisiesto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bisiesto.model.AppModel
import com.example.bisiesto.model.Datos
import kotlin.random.Random

class AppViewmodel : ViewModel() {
    private val model = AppModel()
    private val _datos = MutableLiveData<Datos>()
    val datos : LiveData<Datos> get() = _datos

    fun generarNumero(){
       _datos.value = Datos(model.generarNumero(), 1)
    }

    fun isNumeroGenerado(): Boolean {
        return (datos.value != null && datos.value.numAleatorio != 0)
    }

    fun comprobarBisiesto(respuesta: String) {
        val datosActuales = _datos.value
        datosActuales.estado = model.comprobarBisiesto(_datos.value.numAleatorio, respuesta)
        _datos.value = datosActuales
    }

    fun reset(){
        _datos.value.estado = 1
    }

    fun comprobarDivisible(respuesta: Set<Int>) {
        val datosActuales = _datos.value
        datosActuales.estado = model.comprobarDivisible(_datos.value.numAleatorio, respuesta)
        _datos.value = datosActuales
    }


}