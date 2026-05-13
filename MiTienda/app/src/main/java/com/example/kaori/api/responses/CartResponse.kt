package com.example.kaori.api.responses

import com.example.kaori.model.CartItemDto
import com.example.kaori.model.ProductDto

data class CartResponse(val items: List<CartItemDto>, val distinctProducts: Int, val totalUnits: Int)
