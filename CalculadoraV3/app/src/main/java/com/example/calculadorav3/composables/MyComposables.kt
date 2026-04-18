package com.example.calculadorav3.composables


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculadorav3.viewmodel.MyViewModel

@Composable
fun CalculadoraComposable() {
    val myViewModel = viewModel<MyViewModel>()
    var num1 by rememberSaveable { mutableStateOf("") }
    var num2 by rememberSaveable { mutableStateOf("") }
    val total by myViewModel.data.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                //.height(100.dp)

        ) {
            Row(modifier = Modifier
                .fillMaxWidth()) {
                Text(
                    text = "Número 1:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )

                TextField(
                    value = num1,
                    onValueChange = {
                        num1 = it
                    },
                    label = { Text("Inserte un número") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

            }

            Row(modifier = Modifier
                .fillMaxWidth()) {

                Text(
                    text = "Número 2:",
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    textAlign = TextAlign.Center

                )

                TextField(
                    value = num2,
                    onValueChange = {
                        num2 = it
                    },
                    label = { Text("Inserte un número") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )}

            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
                horizontalArrangement = Arrangement.SpaceEvenly) {

                Button(
                    onClick = {
                        myViewModel.add(num1.toDoubleOrNull(), num2.toDoubleOrNull())
                    }
                ) {
                    Text("+")
                }

                Button(
                    onClick = {
                        myViewModel.sustract(num1.toDoubleOrNull(), num2.toDoubleOrNull())
                    }
                ) {
                    Text("-")
                }

                Button(
                    onClick = {
                        myViewModel.multiply(num1.toDoubleOrNull(), num2.toDoubleOrNull())
                    }
                ) {
                    Text("*")
                }

                Button(
                    onClick = {
                        myViewModel.divide(num1.toDoubleOrNull(), num2.toDoubleOrNull())
                    }
                ) {
                    Text("/")
                }
            }

            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = total?.toString() ?: "Datos inválidos",
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    textAlign = TextAlign.Center
                )
            }

        }
    }

}