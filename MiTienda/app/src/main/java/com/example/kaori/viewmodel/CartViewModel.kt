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

    private val _cartItems = MutableStateFlow<List<CartItemDto>>(emptyList())
    val cartItems = _cartItems.asStateFlow()
    private val _selectedItem = MutableStateFlow<CartItemDto?>(null)
    val selectedItem = _selectedItem.asStateFlow()
    private val _selectedItemProductId = MutableStateFlow<Int?>(null)
    private val _showDeleteDialog = MutableStateFlow(false)
    val showDeleteDialog = _showDeleteDialog.asStateFlow()
    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun getCart(token: String){
        viewModelScope.launch {
            _cartItems.value = model.getCart(token)
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
        _selectedItem.value = item
        _selectedItemProductId.value = productId
        _showDeleteDialog.value = true
    }

    fun cancelDelete() {
        _selectedItem.value = null
        _selectedItemProductId.value = null
        _showDeleteDialog.value = false
    }

    fun confirmDelete(token: String) {
        val productId = _selectedItemProductId.value ?: return
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
            _selectedItem.value = null
            _selectedItemProductId.value = null
            _showDeleteDialog.value = false
        }
    }

    fun clearMessage() {
        _message.value = null
    }


}