package com.example.dogslist.api.responses

/*
Data class para almacenar la respuesta de la API al pedir fotos de una raza en concreto.
Los parámetros se deben llamar igual que los que envía la api.
El mensaje es una lista dado que puede recibir más de una foto.
 */
data class DogsResponse(val status: String, val message: MutableList<String>?)
