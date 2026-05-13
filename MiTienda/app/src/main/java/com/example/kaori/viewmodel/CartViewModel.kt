package com.example.kaori.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaori.model.CartItemDto
import com.example.kaori.model.CartModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {
    private val model = CartModel()

    private val _cartProducts = MutableStateFlow<List<CartItemDto>>(emptyList())
    val cartProducts = _cartProducts.asStateFlow()
    private val _selectedProduct = MutableStateFlow<CartItemDto?>(null)
    val selectedProduct = _selectedProduct.asStateFlow()
    private val _selectedProductId = MutableStateFlow<Int?>(null)
    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog = _showDeleteDialog.asStateFlow()
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun getCart(token: String){
        viewModelScope.launch {
            _cartProducts.value = model.getCart(token)
        }
    }

    fun addProductToCart(token: String, productId: Int, units: Int) {
        viewModelScope.launch {
            val success = model.addProductToCart(
                token = token, productId = productId,
                units = units
            )
            _message.value = if (success) {
                "Producto añadido al carrito"
            } else {
                "No se pudo añadir al producto"
            }
        }
    }

    fun selectItemToDelete(item: CartItemDto, productId: Int) {
        _selectedProduct.value = item
        _selectedProductId.value = productId
        _showDeleteDialog.value = true
    }

    fun cancelDelete() {
        _selectedProduct.value = null
        _selectedProductId.value = null
        _showDeleteDialog.value = false
    }

    fun confirmDelete(token: String) {
        val productId = _selectedProductId.value ?: return
        viewModelScope.launch {
            val success = model.removeProductFromCart(
                token = token,
                productId = productId
            )
            if (success) {
                getCart(token)
                _message.value = "Producto eliminado del carrito"
            } else {
                _message.value = "No se pudo eliminar el producto"
            }
            _selectedProduct.value = null
            _selectedProductId.value = null
            _showDeleteDialog.value = false
        }
    }




}