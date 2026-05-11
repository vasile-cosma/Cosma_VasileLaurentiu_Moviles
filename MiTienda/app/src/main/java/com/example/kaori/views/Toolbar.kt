package com.example.kaori.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.Companion
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.kaori.R
import com.example.kaori.view.Products

@Composable
fun Toolbar(
    token: String,
    user: String,
    url: String,
    navController: NavController
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
           modifier = Modifier
               .weight(1f)
       ) {

            when (selectedTab) {
                0 -> Home(url)
                1 -> Products(navController = navController,
                    token = token)
            }
       }
        PrimaryTabRow(
            selectedTabIndex = selectedTab,
            contentColor = colorResource(id = R.color.kaoriBackground),
            containerColor = colorResource(id = R.color.kaoriGreen),
            indicator =  {
                TabRowDefaults.PrimaryIndicator(
                    color = colorResource(id = R.color.kaoriBackground)
                )
            }

        ) {
            HomeTab(selected = selectedTab == 0, onClick = { selectedTab = 0 })
            ProductsTab(selected = selectedTab == 1, onClick = { selectedTab = 1 })
            CartTab(selected = selectedTab == 2, onClick = { selectedTab = 2 })
        }
    }

}

@Composable
fun HomeTab(selected: Boolean, onClick: () -> Unit) {
    Tab(
        selected = selected,
        onClick = onClick,
        enabled = true,
        text = { Text("Inicio", color = colorResource(id = R.color.kaoriBackground)) },
        icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home/Inicio", tint = colorResource(id = R.color.kaoriBackground)) }
    )
}

@Composable
fun ProductsTab(selected: Boolean, onClick: () -> Unit){
    Tab(
        selected = selected,
        onClick = onClick,
        enabled = true,
        text = { Text("Productos") },
        icon = { Icon(imageVector = Icons.Filled.Backpack, contentDescription = "Catálogo de productos") }
    )
}

@Composable
fun CartTab(selected: Boolean, onClick: () -> Unit){
    Tab(
        selected = selected,
        onClick = onClick,
        enabled = true,
        text = { Text("Carrito") },
        icon = { Icon(imageVector = Icons.Filled.ShoppingCart, contentDescription = "Carrito") }
    )
}