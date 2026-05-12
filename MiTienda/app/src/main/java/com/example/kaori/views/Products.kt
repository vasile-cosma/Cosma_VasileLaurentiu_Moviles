package com.example.kaori.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kaori.R
import com.example.kaori.model.CategoryDto
import com.example.kaori.model.ProductDto
import com.example.kaori.viewmodel.ProductsViewModel
import kotlinx.coroutines.selects.select
import kotlin.math.ceil


@Composable
fun ProductRow(
    product: ProductDto,
    onClick: () -> Unit
) {
    val productCategories = product.categories
        .map { category -> category.name }
        .joinToString(separator = ", ")
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
                model = "http://10.0.2.2:8080/images/products/${product.img}",
                contentDescription = product.name,
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 10.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .padding(end = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = "http://10.0.2.2:8080/images/products/not-available.webp",
                    contentDescription = product.name,
                    modifier = Modifier
                        .size(80.dp)
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
                text = "Marca: ${product.brand.name}",
                fontSize = 16.sp,
                color = Color.DarkGray
            )

            Text(
                text = productCategories,
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

    var currentPage by remember { mutableIntStateOf(0) }
    val pageSize = 5

    var selectedCategoryId by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(Unit) {
        myViewModel.getProducts(token)
    }

    val categories by remember(productsList) {
        derivedStateOf {
            productsList
                .flatMap { it.categories }
                .distinctBy { it.id }
                .sortedBy { it.name }
        }
    }

    val filteredProducts by remember(productsList, selectedCategoryId){
        derivedStateOf {
            if (selectedCategoryId == null){
                productsList
            } else {
                productsList.filter { products ->
                    products.categories.any { category ->
                        category.id.toInt() == selectedCategoryId
                    }
                }
            }
        }
    }

    val totalPages by remember(filteredProducts) {
        derivedStateOf {
            if (filteredProducts.isEmpty()) {
                1
            } else {
                ceil(filteredProducts.size / pageSize.toDouble()).toInt()
            }
        }
    }

    val paginatedProducts by remember(filteredProducts, currentPage) {
        derivedStateOf {
            filteredProducts
                .drop(currentPage * pageSize)
                .take(pageSize)
        }
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

        CategoryFilter(
            categories = categories,
            selectedCategoryId = selectedCategoryId,
            onCategorySelected = { categoryId ->
                selectedCategoryId = categoryId
                currentPage = 0
            }
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
            /*Text(
                text = "${currentPage+1} / $totalPages",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = Color.DarkGray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
            )*/
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(paginatedProducts) { product ->
                    ProductRow(
                        product = product
                    ) {
                        //TODO navController.navigate("productDetail/${product.id}")
                    }
                }
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.kaoriGreen)),
                    onClick = {
                        if (currentPage > 0) {
                            currentPage--
                        }
                    }
                ) { Text("Anterior") }

                Text(
                    text = "${currentPage + 1} / $totalPages",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.kaoriGreen)),
                    onClick = {
                        if (currentPage < totalPages-1) {
                            currentPage++
                        }
                    },
                    enabled = currentPage < totalPages-1
                ) {
                    Text("Siguiente")
                }
            }

        }
    }
}

@Composable
fun CategoryFilter(
    categories: List<CategoryDto>,
    selectedCategoryId: Int?,
    onCategorySelected: (Int?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedButton(
            colors = if (selectedCategoryId == null){
                ButtonDefaults.buttonColors(colorResource(R.color.kaoriGreen))
            } else {
                ButtonDefaults.buttonColors(colorResource(R.color.kaoriBackground))
            },
            onClick = {
                onCategorySelected(null)
            }
        ) {
            Text(
                text = "Todas las categorías",
                fontWeight = FontWeight.Bold,
                color = if (selectedCategoryId == null){
                    Color.White
                } else {
                    Color.Black
                })
        }

        categories.forEach { category ->
            OutlinedButton(
                colors = if (selectedCategoryId == category.id.toInt()){
                    ButtonDefaults.buttonColors(colorResource(R.color.kaoriGreen))
                } else {
                    ButtonDefaults.buttonColors(colorResource(R.color.kaoriBackground))
                },
                onClick = {
                    onCategorySelected(category.id.toInt())
                }
            ) {
                Text(
                    text = category.name,
                    fontWeight = FontWeight.Bold,
                    color = if (selectedCategoryId == category.id.toInt()){
                        Color.White
                    } else {
                        Color.Black
                    }
                )
            }
        }
    }
}