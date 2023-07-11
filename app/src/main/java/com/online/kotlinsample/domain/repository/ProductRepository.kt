package com.online.kotlinsample.domain.repository

import com.online.kotlinsample.data.api.UiState
import com.online.kotlinsample.data.model.Product

interface ProductRepository {

    suspend fun getProductList() : UiState<List<Product>>

    suspend fun getProductDetails(productId: String) : UiState<Product>

}