package com.example.kaori.views

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kaori.R
import com.example.kaori.model.CartItemDto
import com.example.kaori.viewmodel.CartViewModel
import com.example.kaori.viewmodel.ProductsViewModel

@Composable
fun CartRow(
    item: CartItemDto,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected) {
        Color(0xFFE0F2E9)
    } else {
        Color.White
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .border(1.dp, Color.LightGray)
            .clickable {
                onClick()
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = item.productName,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Cantidad: ${item.units}",
                fontSize = 16.sp,
                color = Color.DarkGray
            )

            Text(
                text = "Precio/unidad: ${item.unitPrice} €",
                fontSize = 16.sp,
                color = Color.Black
            )

            Text(
                text = "Total: ${"%.2f".format(item.totalPrice)} €",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun Cart(
    token: String
) {
    val cartViewModel: CartViewModel = viewModel()
    val productsViewModel: ProductsViewModel = viewModel()

    val cartItems by cartViewModel.cartProducts.collectAsState()
    val allProducts by productsViewModel.allProducts.collectAsState()
    val selectedItem by cartViewModel.selectedProduct.collectAsState()
    val showDeleteDialog by cartViewModel.showDeleteDialog.collectAsState()
    val message by cartViewModel.message.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        cartViewModel.getCart(token)
    }

    LaunchedEffect(message) {
        if (message != null) {
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        Text(
            text = "Mi Carrito",
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        if (cartItems.isEmpty()) {
            Text(
                text = "El carrito está vacío. ¿Te vas a ir sin el mejor té?",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                items(cartItems) { item ->
                    CartRow(
                        item = item,
                        selected = selectedItem?.productName == item.productName,
                        onClick = {
                            val productId = allProducts.find {it.name == item.productName}?.id
                            if (productId != null) {
                                cartViewModel.selectItemToDelete(item, productId)
                            }
                        }
                    )
                }
            }
        }
    }

    if (showDeleteDialog && selectedItem != null) {
        AlertDialog(
            onDismissRequest = {
                cartViewModel.cancelDelete()
            },
            title = {
                Text("Eliminar producto")
            },
            text = {
                Text("¿Deseas eliminar ${selectedItem!!.productName} del carrito?")
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.kaoriGreen)
                    ),
                    onClick = {
                        cartViewModel.confirmDelete(token)
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(Color.Red),
                    onClick = {
                        cartViewModel.cancelDelete()
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}