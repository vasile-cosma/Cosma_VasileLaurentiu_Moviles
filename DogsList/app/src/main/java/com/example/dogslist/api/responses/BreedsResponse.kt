package com.example.dogslist.api.responses

/*
Data class para almacenar la respuesta de la API al pedir todas las razas.
Los parámetros se deben llamar igual que los que envía la api.
El segundo es un Map dado que puede haber subrrazas, en cuyo caso la estructura de datos
es un mapa en el cual:
 - Clave: raza
 - Valor: subraza(s) de la raza
 */
data class BreedsResponse(val status: String, val message: Map<String, List<String>>?)
