package com.example.kaori.controllers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.kaori.views.*



@Composable
fun Navigation() {
    val myNavController = rememberNavController()

    val tokenState = remember { mutableStateOf("") }
    val userState = remember {mutableStateOf("")}

    NavHost(
        navController = myNavController,
        startDestination = "login"
    ) {
        composable("login") {
            Login(
                onLoginSuccess = { token, username ->
                    tokenState.value = token
                    userState.value = username

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
                navController = myNavController,
                onLogout = {
                    tokenState.value = ""
                    userState.value = ""

                    myNavController.navigate("login"){
                        popUpTo("home") { inclusive = true}
                    }
                }
            )
        }

        composable("productDetail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            ProductDetail(
                navController = myNavController,
                token = tokenState.value,
                productId = productId.toInt(),
            )
        }



    }

}