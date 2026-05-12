package com.example.kaori.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicSecureTextField
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kaori.viewmodel.LoginViewModel
import com.example.kaori.R

@Composable
fun KaoriHeader() {

}

@Composable fun Login(
    loginViewModel : LoginViewModel = viewModel(),
    onLoginSuccess: (String, String) -> Unit
) {
    var username by remember { mutableStateOf("") }
    val passwordState = remember { TextFieldState() }
    val loginState by loginViewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState?.accessToken != null) {
            onLoginSuccess(loginState!!.accessToken!!, username)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.kaori_logo),
            contentDescription = "Kaori Logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 8.dp)
        )

        Text(
            text = "Bienvenido",
            color = colorResource(id = R.color.kaoriGreen),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
            },
            Modifier
                .fillMaxWidth()
                .padding(6.dp),
            placeholder = { Text("Nombre de usuario", color = Color.Gray) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray,
                focusedLabelColor = Color.Gray,
                cursorColor = Color.Gray
            )
        )
        PasswordTextField(state = passwordState)

        if (loginState != null && loginState?.accessToken == null) {
            Text(
                text = "ERROR AL INICIAR SESIÓN",
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp)
            )
        }

        Button(
            onClick = {
                loginViewModel.login(username, passwordState.text.toString())
            },
            modifier = Modifier.fillMaxWidth().padding(6.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.kaoriGreen))
        ) {
            Text("Iniciar sesión")
        }
    }

}

// EJEMPLO de la documentación:
@Composable
fun PasswordTextField(state: TextFieldState = remember { TextFieldState() }) {
    var showPassword by remember { mutableStateOf(false) }
    BasicSecureTextField(
        state = state,
        textObfuscationMode =
            if (showPassword) {
                TextObfuscationMode.Visible
            } else {
                TextObfuscationMode.RevealLastTyped
            },
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(6.dp))
            .padding(6.dp),
        decorator = { innerTextField ->
            Box(modifier = Modifier.fillMaxWidth()) {
                if (state.text.isEmpty()) {
                    Text(
                        text = "Contraseña",
                        color = Color.Gray,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp, end = 48.dp)
                ) {
                    innerTextField()
                }
                Icon(
                    if (showPassword) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    },
                    contentDescription = "Activa/desctiva visibilidad de contraseña",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .requiredSize(48.dp).padding(16.dp)
                        .clickable { showPassword = !showPassword }
                )
            }
        }
    )


}