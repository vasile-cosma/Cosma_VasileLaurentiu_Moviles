package com.example.kaori.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaori.model.ProductDto
import com.example.kaori.model.ProductsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {
    private val model = ProductsModel()
    private val _productsData = MutableStateFlow<List<ProductDto>>(emptyList())
    val productsData = _productsData.asStateFlow()

    fun getProducts(token: String) {
        viewModelScope.launch {
            val products = model.getProducts(token)
            _productsData.value = products
        }
    }
}