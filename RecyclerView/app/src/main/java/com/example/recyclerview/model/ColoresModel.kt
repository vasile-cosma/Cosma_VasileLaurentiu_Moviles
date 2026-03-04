package com.example.recyclerview.model

import com.example.recyclerview.entities.MiColor


class ColoresModel {
    var colores = mutableListOf<MiColor>(MiColor("Rojo", "#FF0000"),
        MiColor("Azul", "#0000FF"),
        MiColor("Verde", "#008000"),
        MiColor("Amarillo", "#FFFF00"),
        MiColor("Cian", "#00FFFF"),
        MiColor("Morado", "#800080"),
        MiColor("Lima", "#00FF00"),
        MiColor("Rosa", "#FF00FF"),
        MiColor("Turquesa", "#008080"),
        MiColor("Oliva", "#808000"),
        MiColor("Indigo", "#4B0082"),
        MiColor("Naranja","#FFA420"),
        MiColor("Marron","#895129"))

    suspend fun retornarLista() : Datos {
        return Datos("ok", colores)
    }

}