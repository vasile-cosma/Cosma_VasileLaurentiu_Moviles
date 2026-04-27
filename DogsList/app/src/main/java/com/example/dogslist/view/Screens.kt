package com.example.dogslist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.dogslist.viewmodel.DogsViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.dogslist.model.BreedListData

/*
Fila con cada raza de perro.
Recibe tres parámetros:
 - Un BreedListData (contiene raza y subraza si la hubiera).
 - Un booleano indicando si la fila es par o impar.
 - Una función que se ejecuta cuando se hace click en la fila para ver sus imágenes.
 */
@Composable
fun BreedRow(
    breed: BreedListData,
    isEven: Boolean,
    onClick: () -> Unit
) {
    val breedText: String
    val backgroundColor: Color
    val color: Color

    // Si hay subraza, la concatenamos a la raza principal.
    if (breed.subbreed != null) {
        breedText = "${breed.breed} ${breed.subbreed}"
    } else {
        breedText = breed.breed
    }

    /*
    Alternamos colores entre columnas pares e impares,
    al igual que en el ejemplo del enunciado.
     */
    if (isEven){
        backgroundColor = Color.DarkGray
        color = Color.White
    } else {
        backgroundColor = Color.White
        color = Color.Black
    }

    Row (
        modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
        .clickable{
            onClick()
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = breedText,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = color,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.Black))
    }
}

/*
Vista para mostrar el listado de razas.
Recibe nuestro NavigationController como parámetro.
 */
@Composable
fun Breeds(navController: NavController) {
    val myViewModel: DogsViewModel = viewModel()
    val breedListState by myViewModel.breedListState.collectAsState()

    /*
     Cargamos las razas de perro invocando al metodo de nuestro ViewModel.
     "Unit" en el metodo ya que nuestra fucnión getBreed no recibe parámetros.
     */
    LaunchedEffect(Unit) {
        myViewModel.getBreeds()
    }

        Column (
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
        ) {
            // Título
            Text(
                text = "Listado de Razas",
                color = Color.Blue,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            /*
             Scroll horizontal con la paginación alfabética.
             Recibe los parámetros a partir de nuestro estado en el ViewModel.
             */
            AlphabetPages(
                availableChars = breedListState.availableChars,
                selectedChar = breedListState.selectedChar,
                // Actualizamos la letra seleccionada y se la pasamos al ViewModel.
                onCharSelected = { myViewModel.selectChar(it) }
            )

            // Scroll vertical con las razas de perros.
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 1.dp)
            ) {
                // En vez de items, usamos itemsIndexed para calcular par/impar.
                itemsIndexed(breedListState.filteredBreeds) { index, breed ->
                    BreedRow(breed, isEven = index % 2==0)
                    /*
                     Formamos la ruta con la raza (y subraza si la hubiera) de esta fila.
                     Posteriormente la pasamos al NavigationController.
                     */
                    {
                        val route : String
                        if (breed.subbreed != null) {
                            route = "images/${breed.breed}?subbreed=${breed.subbreed}"
                        } else {
                            route = "images/${breed.breed}"
                        }
                        navController.navigate(route) {
                        }
                    }
                }
            }
        }
}

/*
Paginación alfabética. Recibe tres parámetros:
 - Un listado de caracteres únicos que representan las letras disponibles.
 - Una letra (Char?) que representa la letra seleccionada por el usuario.
 - Una función que se ejecuta cuando se selecciona una letra.
 */
@Composable
fun AlphabetPages(
  availableChars: Set<Char>,
  selectedChar: Char?,
  // Parámetro función, por eso nos pide "-> Unit"
  onCharSelected: (Char?) -> Unit
) {
    // Al ser un scroll horizontal, usamos LazyRow en vez de LazyColumn.
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        // Rellenamos el primer item manualmente sin char seleccioando para mostrar todas las razas.
        item {
            Letter(
                letter = "∞",
                isSelected = selectedChar == null,
                // De esta forma es clickable aunque no esté en el abecedario.
                isAvailable = true,
                onClick = {
                    onCharSelected(null)
                }
            )
        }
        // Rellenamos el resto de items con las letras del abecedario.
        items(('A'..'Z').toList()) { char ->
            Letter(
                letter = char.toString(),
                isSelected = selectedChar == char,
                isAvailable = char in availableChars,
                onClick = {
                    if (char in availableChars) {
                        onCharSelected(char)
                    }
                }
            )
        }
    }

}

/*
Letra del abecedario para filtrar las razas en nuestro scroll horizontal.
 Recibe cuatro parámetros:
 - Un String que representa la letra.
 - Un booleano que indica si la letra está seleccionada.
 - Un booleano que indica si la letra está disponible.
 - Una función que se ejecuta cuando se selecciona la letra.
 */
@Composable
fun Letter(
    letter: String,
    isSelected: Boolean,
    isAvailable: Boolean,
    // Parámetro función, por eso nos pide "-> Unit"
    onClick: () -> Unit
) {
    // Atributo dinámico para el color de fondo
    val background = when {
        isSelected -> Color.Blue
        isAvailable -> Color.White
        else -> Color.Gray
    }
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(background)
            // Solo se puede pulsar si la letra está disponible. Lanza el evento onClick
            .clickable(enabled = isAvailable, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = letter,
            fontSize = 14.sp,
            color =
                if (isSelected) {
                    Color.White
                } else if (isAvailable) {
                    Color.Black
                } else {
                Color.LightGray
                }
        )
    }
}

/*
Vista para mostrar las imágenes de una raza en concreto.
Recibe tres parámetros:
 - Un String con la raza a mostrar
 - Un String con la subraza a mostrar (opcional)
 - Un NavigationController para poder navegar hacia atrás.
 */
@Composable
fun DogsFromBreed(
    breed: String,
    subbreed: String?,
    navController: NavController
) {
    val myViewModel: DogsViewModel = viewModel()
    val images by myViewModel.dogsImages.collectAsState()
    val title: String
    // "Escuchamos" el booleano directamente desde el composable.
    var showPopUp by remember { mutableStateOf(false) }

    if (subbreed != null) {
        title = "Fotos de $breed $subbreed"
    } else {
        title = "Fotos de $breed"
    }

    /*
    Cargamos las imágenes de la raza seleccionada invocando al metodo de nuestro ViewModel.
     */
    LaunchedEffect(breed, subbreed) {
        myViewModel.getDogs(breed, subbreed)
    }

    // Pop up decorativo, sin funcionalidad. Quería experimentar con Compose.
    if (showPopUp) {
        AlertDialog(
            // Cierra al pulsar fuera
            onDismissRequest = { showPopUp = false },
            title = { Text("Adoptar cerca de mí")},
            text = { Text("¿Quieres buscar un refugio cercano donde adoptar un $breed?") },
            confirmButton = {
                Button(onClick = { showPopUp = false }) {
                    Text("Buscar")
                }
            },
            dismissButton = {
                Button(onClick = { showPopUp = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        // Título
        Text(
            text = title,
            color = Color.Blue,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 3.dp, horizontal = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón para volver utilizando el remember de nuestro NavigationController.
            Button (
                onClick = { navController.popBackStack() }
            ) {
                Text(text = "Volver")
            }

            // Botón decorativo que muestra un pop up sin funcionalidad.
            Button (
                onClick = { showPopUp = true }
            ) {
                Text(text = "Adoptar")
            }
        }

        /*
        LazyColumn que contiene las imágenes
         */
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(images) { image ->
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Black)
                ) {
                    // Componente de coil para mostrar la imagen
                    AsyncImage(
                        model = image,
                        contentDescription = "Foto de $breed s",
                        alignment = Alignment.Center,
                    )
                }
            }
        }
    }

}

