package com.example.dogslist.controllers

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dogslist.view.Breeds
import com.example.dogslist.view.DogsFromBreed

/*
NavigationController para poder navegar entre pantallas.
 */
@Composable
fun Navigation() {
    val myNavController = rememberNavController()

    NavHost(
        navController = myNavController,
        // Por defecto comienza en el listado de razas.
        startDestination = "ListaRazas"
    ) {
        composable("ListaRazas") {
            Breeds(myNavController)
        }

        // Breed es un parámetro de la URL, mientras que subbreed es parte de la query al ser nullable.
        composable(route = "images/{breed}?subbreed={subbreed}",
            arguments = listOf(
                navArgument("breed") { type = NavType.StringType },
                navArgument("subbreed") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { backStackEntry ->
            val breed = backStackEntry.arguments?.getString("breed")
            val subbreed = backStackEntry.arguments?.getString("subbreed")
            DogsFromBreed(breed = breed!!, subbreed = subbreed, navController = myNavController)

        }
    }
}