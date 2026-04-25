package com.example.dogslist.model

/*
Data class utilizada para simplificar el tratado de datos del Map recibido por la API de razas.
Almacena la raza y la subraza (si la hubiera).
 */
data class BreedListData(val breed: String, val subbreed: String?)
