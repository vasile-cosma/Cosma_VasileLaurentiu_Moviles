package com.example.kaori.model

data class ProductDto(
    val id: Int,
    val code: String,
    val name: String,
    val description: String,
    val img: String?,
    val price: Double,
    val discount: Int,
    val stock: Int,
    val brand: BrandDto,
    val categories: List<CategoryDto>)
