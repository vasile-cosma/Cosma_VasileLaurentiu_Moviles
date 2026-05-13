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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.kaori.R
import com.example.kaori.model.CartModel
import com.example.kaori.model.CategoryDto
import com.example.kaori.model.ProductDto
import com.example.kaori.viewmodel.CartViewModel
import com.example.kaori.viewmodel.ProductsViewModel

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
    val categories by myViewModel.categories.collectAsState()
    val selectedCategoryId by myViewModel.selectedCategoryId.collectAsState()
    val currentPage by myViewModel.currentPage.collectAsState()
    val totalPages by myViewModel.totalPages.collectAsState()


    LaunchedEffect(Unit) {
        myViewModel.getProducts(token)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
    ) {
        Text(
            text = "Catálogo",
            color = Color.Black,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = " Los mejores tés, a un click",
            color = Color.Black,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(top = 1.dp)
        )
        CategoryFilter(
            categories = categories,
            selectedCategoryId = selectedCategoryId,
            onCategorySelected = { categoryId ->
                myViewModel.selectCategory(categoryId)
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(productsList) { product ->
                    ProductRow(
                        product = product
                    ) {
                        navController.navigate("productDetail/${product.id}")
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
                        myViewModel.previousPage()
                    },
                    enabled = currentPage == 0
                ) { Text("Anterior") }

                Text(
                    text = "${currentPage + 1} / $totalPages",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    colors = ButtonDefaults.buttonColors(colorResource(R.color.kaoriGreen)),
                    onClick = {
                       myViewModel.nextPage()
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

@Composable
fun ProductDetail(
    navController: NavController,
    token: String,
    productId: Int
) {
    val myViewModel: ProductsViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val allProducts by myViewModel.allProducts.collectAsState()


    var units by remember { mutableIntStateOf(1) }


    LaunchedEffect(Unit) {
        myViewModel.getProducts(token)
    }

    val product = allProducts.find { it.id == productId}

    if (product == null) {
        Text(
            text = "Cargando producto...",
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp)
        )
        return
    }

    val productCategories = if (product.categories.isEmpty()) {
        "Sin categoría"
    } else {
        product.categories
            .map {category -> category.name}
            .joinToString(separator = ",")
    }

    val image = if (product.img != null) {
        "http://10.0.2.2:8080/images/products/${product.img}"
    } else {
        "http://10.0.2.2:8080/images/products/not-available.webp"
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment =  Alignment.CenterHorizontally
    ) {
        KaoriHeader()

        Text(
            text = product.name,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        AsyncImage(
            model = image,
            contentDescription = product.name,
            modifier = Modifier
                .size(220.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "Marca: ${product.brand.name}",
            fontSize = 18.sp,
            color = Color.DarkGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Text(
            text = "Categorías: $productCategories",
            fontSize = 18.sp,
            color = Color.DarkGray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Text(
            text = product.description,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Text(
            text = "Precio: ${product.price}€",
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        if (product.discount > 0) {
            Text(
                text = "Descuento: ${product.discount}%",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
           Button(
               colors = ButtonDefaults.buttonColors(colorResource(R.color.kaoriGreen)),
               onClick = {
                   if (units >1) {
                       units--
                   }
               }
           ) {
               Text("-")
           }

            Text(
                text = units.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
            )

            Button (
                colors = ButtonDefaults.buttonColors(colorResource(R.color.kaoriGreen)),
                onClick = {
                    if (units < product.stock) units++
                }

                ) { Text("+")}

            Button (
                colors = ButtonDefaults.buttonColors(colorResource(R.color.kaoriGreen)),
                onClick = {
                    cartViewModel.addProductToCart(
                        token = token,
                        productId = product.id,
                        units = units
                    )
                },
                modifier = Modifier
                    .padding(start = 6.dp)


            ) { Text("Añadir al carrito")}
        }

        Button(
            colors = ButtonDefaults.buttonColors(Color.Red),
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Text("Volver")
        }

    }
}