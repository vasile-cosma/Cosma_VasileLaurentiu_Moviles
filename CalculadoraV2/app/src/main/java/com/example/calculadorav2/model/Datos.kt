package com.example.calculadorav2.model

data class Datos(val historial: String, val num1: String, val num2: String, val operacion: String, val terminado: Boolean, val error: Boolean, val mensajeError: String) {
}