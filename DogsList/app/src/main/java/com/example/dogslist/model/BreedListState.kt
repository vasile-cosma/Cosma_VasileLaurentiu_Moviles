package com.example.dogslist.model

/*
Data class para almacenar el estado de la letra seleccionada y filtrar la lista de
razas conforme a ello.
 */
data class BreedListState(
    val allBreeds: List<BreedListData> = emptyList(),
    val selectedChar: Char? = null
) {
    /*
    Si no se ha seleccionado ninguna letra, se muestran todas las razas.
    De lo contrario, filtramos el listado de razas por la letra seleccionada.
     */
    val filteredBreeds: List<BreedListData>
        get() = if (selectedChar == null) {
            allBreeds
        } else {
            allBreeds.filter { it.breed.first().uppercaseChar() == selectedChar.uppercaseChar() }
        }

    /*
    Marcamos como letras disponibles aquellas que sean el primer caracter de alguna raza.
     */
    val availableChars: Set<Char>
        get() = allBreeds.map { it.breed.first().uppercaseChar() }.toSet()

}
