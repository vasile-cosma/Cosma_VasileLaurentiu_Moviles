package com.example.dogslist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogslist.model.BreedListData
import com.example.dogslist.model.BreedListState
import com.example.dogslist.model.MyModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/*
ViewModel que actúa de puente entre el modelo y la vista.
Actualiza el modelo en base a las acciones del usuario en la vista.
 */
class DogsViewModel : ViewModel() {
    val state = MyModel()
    private val _breedData = MutableStateFlow<List<BreedListData>>(emptyList())
    val breedData = _breedData.asStateFlow()
    private val _breedListState = MutableStateFlow(BreedListState())
    val breedListState = _breedListState.asStateFlow()
    private val _dogsImages = MutableStateFlow<List<String>>(emptyList())
    val dogsImages = _dogsImages.asStateFlow()

    // Recibimos la letra seleccionada de la vista.
    fun selectChar(char: Char?) {
        viewModelScope.launch {
        _breedListState.value = _breedListState.value.copy(selectedChar = char)
            }
    }

    /*
    Método para obtener las razas de perro de la API.
     - Si la petición que le llega no es exitosa, la devuelve tal cual en un List.
     - En caso contrario, itera cada raza (y subraza si la hubiere) de la respuesta obtenida
     y almacena los datos en una lista que se utiliza para actualizar el modelo.
     */
    fun getBreeds() {
        viewModelScope.launch {

            state.breed = state.getBreeds()
            var breeds = mutableListOf<BreedListData>()

            if (state.breed.status != "error"){
                for ((breed, subbreed) in state.breed.message.orEmpty()) {
                    if (subbreed.isNullOrEmpty()) {
                        breeds.add(BreedListData(breed, null))
                    } else {
                        subbreed.forEach { breeds.add(BreedListData(breed, it)) }
                    }
                }
            } else {
                breeds.add(BreedListData("error", null))
            }

            _breedListState.value = _breedListState.value.copy(allBreeds = breeds)
        }
    }

    /*
    Metodo que solicita al modelo las imágenes de la raza seleccionada por
    el usuario en la vista. En caso de obtener un cuerpo null, devuelve una lista vacía.
     */
    fun getDogs(breed: String, subbreed: String?) {
        viewModelScope.launch {
           _dogsImages.value = state.getDogs(breed, subbreed).message.orEmpty()
        }

    }
}