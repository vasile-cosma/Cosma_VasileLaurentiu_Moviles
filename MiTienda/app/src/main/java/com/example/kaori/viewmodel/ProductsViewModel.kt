package com.example.kaori.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaori.model.CategoryDto
import com.example.kaori.model.ProductDto
import com.example.kaori.model.ProductsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.ceil

class ProductsViewModel : ViewModel() {
    private val model = ProductsModel()
    private val _productsData = MutableStateFlow<List<ProductDto>>(emptyList())
    val productsData = _productsData.asStateFlow()
    private val pageSize = 5
    private val _allProducts = MutableStateFlow<List<ProductDto>>(emptyList())
    val allProducts = _allProducts.asStateFlow()
    private val _categories = MutableStateFlow<List<CategoryDto>>(emptyList())
    val categories = _categories.asStateFlow()
    private val _selectedCategoryId = MutableStateFlow<Int?>(null)
    val selectedCategoryId = _selectedCategoryId.asStateFlow()
    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()
    private val _totalPages = MutableStateFlow(1)
    val totalPages = _totalPages.asStateFlow()


    fun getProducts(token: String) {
        viewModelScope.launch {
            val products = model.getProducts(token)
            _allProducts.value = products

            _categories.value = products
                .flatMap { product -> product.categories }
                .distinctBy { category -> category.id }
                .sortedBy { category -> category.name }

            _selectedCategoryId.value = null
            _currentPage.value = 0

            applyFiltersAndPagination()

        }
    }

    fun selectCategory(categoryId: Int?) {
        _selectedCategoryId.value = categoryId
        _currentPage.value = 0

        applyFiltersAndPagination()
    }

    fun nextPage(){
        if (_currentPage.value < _totalPages.value -1){
            _currentPage.value = _currentPage.value +1
            applyFiltersAndPagination()
        }
    }

    fun previousPage() {
        if (_currentPage.value > 0 ){
            _currentPage.value = _currentPage.value -1
            applyFiltersAndPagination()
        }
    }


    fun applyFiltersAndPagination() {
        val products = _allProducts.value

        val filteredProducts = if(_selectedCategoryId.value == null){
            products
        } else {
            products.filter { product ->
                product.categories.any { category ->
                    category.id.toInt() == _selectedCategoryId.value
                }
            }
        }

        _totalPages.value = if (filteredProducts.isEmpty()) {
            1
        } else {
            ceil(filteredProducts.size / pageSize.toDouble()).toInt()
        }

        _productsData.value = filteredProducts
            .drop(_currentPage.value * pageSize)
            .take(pageSize)
    }
}