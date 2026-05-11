package com.example.kaori.controllers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kaori.view.Products
import com.example.kaori.views.Home
import com.example.kaori.views.Login
import com.example.kaori.views.Toolbar

/*
NavigationController para poder navegar entre pantallas.
 */
@Composable
fun Navigation() {
    val myNavController = rememberNavController()

    val tokenState = remember { mutableStateOf("") }
    val userState = remember {mutableStateOf("")}

    NavHost(
        navController = myNavController,
        // Por defecto comienza en el listado de razas.
        startDestination = "login"
    ) {
        composable("login") {
            Login(
                onLoginSuccess = { token ->
                    tokenState.value = token

                    myNavController.navigate("home") {
                        popUpTo("login") { inclusive = true } // evita volver al login con "atrás"
                    }
                }
            )
        }
        composable("home") {
            Toolbar(
                token = tokenState.value,
                user = userState.value,
                url = "http://10.0.2.2:8080/",
                navController = myNavController
            )
        }


    }

}