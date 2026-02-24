package com.example.calculadorav2.model

import android.util.Log

class MainState {
    var historial: String = ""
    var num1: String = ""
    var num2: String = ""
    var operacion = ""
    var terminado = false
    var error = false
    var mensajeError = ""


    fun limpiar(): Datos {
        historial = ""
        num1 = ""
        num2 = ""
        terminado = false
        operacion = ""
        error = false
        mensajeError = ""
        return Datos(num1,num2, "", "", false, false ,mensajeError)
    }
    fun mostrarHistorial(texto: String): Datos{
        if (terminado){
            limpiar()
            terminado = false
        }
        if (error) error = false
        if (!operacion.isEmpty()) {
            error = true
            mensajeError = "AVISO: Debe elegir números"
            return Datos(historial, num1, num2, "", terminado, error, mensajeError)
        } else {
            historial+=texto
            num1 = num2
            num2 = ""
            operacion = texto
            Log.d("mi tag:", num2)
            return Datos(historial, num1, num2, "", terminado, error, mensajeError)
        }

    }
    fun anyadirTexto(texto: String): Datos {
        if (error) error = false
        if (terminado){
            limpiar()
            terminado = false
        }
        num2+=texto
        historial+=texto
        Log.d("mi tag:", num2)
        return Datos(historial, num1, num2, operacion, false, error, mensajeError)
    }

    fun calcular(): Datos {
        if (error) {
            error = false
            mensajeError = ""
        }
        if (num1.isEmpty()) {
            limpiar()
            error = true
            mensajeError = "AVISO: operación inválida"
            return Datos(historial, num1, num2, operacion, terminado, error, mensajeError)
        }
        if (terminado){
            error = true
            mensajeError = "AVISO: operación inválida"
            return Datos(historial, num1, num2, operacion, terminado, error, mensajeError)
        }
        when(operacion) {
           "+" -> sumar()
           "-" -> restar()
            "*" -> multiplicar()
            "/" -> dividir()
        }
        val total = num2
        historial = historial + "=" + total
        num1 = ""
        num2 = ""
        return Datos(historial,num1,total,operacion, terminado, error, mensajeError)
    }

    fun sumar() {
        num2 = (num1.toDouble() + num2.toDouble()).toString()
        operacion = ""
        terminado = true
    }

    fun restar(){
        num2 = (num1.toDouble() - num2.toDouble()).toString()
        operacion = ""
        terminado = true
    }

    fun multiplicar(){
        num2 = (num1.toDouble() * num2.toDouble()).toString()
        operacion = ""
        terminado = true
    }

    fun dividir(){
        num2 = (num1.toDouble() / num2.toDouble()).toString()
        operacion = ""
        terminado = true
    }

}