package com.online.kotlinsample.domain.usecases

import com.online.kotlinsample.data.api.UiState
import com.online.kotlinsample.data.model.Product
import com.online.kotlinsample.domain.repository.ProductRepository

class ProductDetailsUseCases(private val productRepository: ProductRepository) {

    suspend operator fun invoke(productId: String): UiState<Product>{
        return productRepository.getProductDetails(productId)
    }
}