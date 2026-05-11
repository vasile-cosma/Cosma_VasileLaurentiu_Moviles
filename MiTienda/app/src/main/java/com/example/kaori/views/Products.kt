package com.example.kaori.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kaori.model.ProductDto
import com.example.kaori.viewmodel.ProductsViewModel


@Composable
fun ProductRow(
    product: ProductDto,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(1.dp, Color.LightGray)
            .clickable {
                onClick()
            }
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (product.img != null) {
            AsyncImage(
                model = "http://10.0.2.2:8000/images/products/${product.img}",
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 10.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 10.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Sin imagen",
                    color = Color.Black,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = product.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = product.brand.name,
                fontSize = 16.sp,
                color = Color.DarkGray
            )

            Text(
                text = "${product.price} €",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            if (product.discount > 0) {
                Text(
                    text = "Descuento: ${product.discount}%",
                    fontSize = 14.sp,
                    color = Color(0xFF2E7D32)
                )
            }

            Text(
                text = "Stock: ${product.stock}",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}


@Composable
fun Products(
    navController: NavController,
    token: String
) {
    val myViewModel: ProductsViewModel = viewModel()
    val productsList by myViewModel.productsData.collectAsState()

    LaunchedEffect(Unit) {
        myViewModel.getProducts(token)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        Text(
            text = "Listado de Productos",
            color = Color(0xFF2E7D32),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        if (productsList.isEmpty()) {
            Text(
                text = "No hay productos disponibles",
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
                items(productsList) { product ->
                    ProductRow(
                        product = product
                    ) {
                        navController.navigate("productDetail/${product.id}")
                    }
                }
            }
        }
    }
}