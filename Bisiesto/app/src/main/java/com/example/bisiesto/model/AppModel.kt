package com.example.bisiesto.model

class AppModel {
    fun generarNumero(): Int {
        return (1900..2200).random()
    }

    fun esBisiesto(anyo: Int): Boolean {
        if (anyo % 400 == 0) return true
        if (anyo % 4 == 0 && anyo % 100 != 0) return true
        return false
    }

    fun comprobarBisiesto(anyo: Int, respuesta: String): Int {
        val esBisiesto = esBisiesto(anyo)
        return when (respuesta){
            "SI" -> {
                return if (esBisiesto) 0
                else -1
            }
            "NO" -> {
                return if (esBisiesto) -1
                else 0
            }
            else -> -2
        }
    }

    fun comprobarDivisible(numAleatorio: Int, respuesta: Set<Int>): Int {
        var solucion = mutableSetOf<Int>()
        if (numAleatorio % 2 == 0) solucion.add(2)
        if (numAleatorio % 3 == 0) solucion.add(3)
        if (numAleatorio % 5 == 0) solucion.add(5)
        if (numAleatorio % 10 == 0) solucion.add(10)
        if (solucion.isEmpty()) solucion.add(0)
        println("SOLUCION:$solucion")
        if (solucion == respuesta) return 0 else return -1


    }


}