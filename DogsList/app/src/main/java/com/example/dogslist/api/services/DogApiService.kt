package com.example.dogslist.api.services

import com.example.dogslist.api.responses.BreedsResponse
import com.example.dogslist.api.responses.DogsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/*
Servicio para hacer peticiones a la API y recibir sus respuestas.
En cada metodo se hace una petición GET a la dirección correspondiente y devolvemos
un wrapper con la respuesta correspondinete.
 */
interface DogApiService {

    @GET("breed/{breed}/images")
    suspend fun getDogsByBreed(@Path("breed") breed: String):
            Response<DogsResponse>

    @GET("breeds/list/all")
    suspend fun getBreeds():
            Response<BreedsResponse>

    @GET("breed/{breed}/{subbreed}/images")
    suspend fun getDogsBySubbreed(
        @Path("breed") breed: String,
        @Path("subbreed") subbreed: String
    ):
            Response<DogsResponse>
}