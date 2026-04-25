package com.example.dogslist.model

import com.example.dogslist.api.RetrofitApi
import com.example.dogslist.api.responses.BreedsResponse
import com.example.dogslist.api.responses.DogsResponse
import retrofit2.Response

/*
Esta clase almacena los datos obtenidos de las peticiones hechas a la API.
 */
class MyModel {
    val retrofitApi = RetrofitApi()
    lateinit var  breed: BreedsResponse

    /*
    Función que hace una petición a la API para obtener todas las razas y subrazas de perros.
     - Si la petición es exitosa, devuelve BreedsResponse con el status recibido y
     el diccionario de razas-subrazas en el cuerpo.
     - Si la petición falla, devuelve BreedsResponse con el status "error" y el cuerpo null.
     */
    suspend fun getBreeds() : BreedsResponse {
        val response = retrofitApi.retrofitService.getBreeds()
        val breedResponse : BreedsResponse

        if (response.isSuccessful && response.body()!=null){
            breedResponse = BreedsResponse(response.body()!!.status, response.body()!!.message)
        } else {
            breedResponse = BreedsResponse("error", null)
        }

        return breedResponse
    }

    /*
    Función que hace una petición a la API para obtener imágenes de una raza/subraza en concreto.
    Recibe dos parámetros: raza y subraza.
     - Si no se indica subraza, pide imágenes de una raza en concreto con el metodo getDogsByBreed(breed) de DogApiService.
     - En caso contrario, pide imágenes de la subrraza indicada con el metodo getDogsBySubbreed(breed, subbreed) de DogApiService.
     - Si la petición no es exitosa, devuelve un DogsResponse con el status "error" y el cuerpo null.
     - Si la petición es exitosa, devuelve un DogResponde con el status recibido por la API y las imágenes en el cuerpo.
     */
    suspend fun getDogs(breed: String, subbreed: String?) : DogsResponse {
        var response: Response<DogsResponse>
        val dogsResponse: DogsResponse


        if (subbreed == null) {
            response = retrofitApi.retrofitService.getDogsByBreed(breed)
        } else {
            response = retrofitApi.retrofitService.getDogsBySubbreed(breed, subbreed)
        }

        if (response.isSuccessful && response.body()!=null){
            dogsResponse = DogsResponse(response.body()!!.status, response.body()!!.message)
        } else {
            dogsResponse = DogsResponse("error", null)
        }

        return dogsResponse

    }


}
