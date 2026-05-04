package com.example.kaori.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Toolbar(
    token: String,
    user: String,
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       Row(
           modifier = Modifier
               .fillMaxWidth(),
           verticalAlignment = Alignment.Bottom
       ) {
           PrimaryTabRow(
               selectedTabIndex = selectedTab
           ) {

           }
       }
    }

}

@Composable
fun HomeTab() {
    Tab(
        enabled = true,
        text = "Inicio",
        icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") }
    )
}

@Composable
fun ProductsTab(){

}

@Composable
fun CartTab(){

}