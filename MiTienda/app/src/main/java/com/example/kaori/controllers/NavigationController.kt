package com.example.kaori.controllers

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kaori.views.Login

/*
NavigationController para poder navegar entre pantallas.
 */
@Composable
fun Navigation() {
    val myNavController = rememberNavController()

    NavHost(
        navController = myNavController,
        // Por defecto comienza en el listado de razas.
        startDestination = "login"
    ) {
        composable("login") {
            Login(
                onLoginSuccess = {
                    myNavController.navigate("home") {
                        popUpTo("login") { inclusive = true } // evita volver al login con "atrás"
                    }
                }
            )
        }
       /* TODO vista aun no está creada

           composable("home") {
        }*/
    }
}