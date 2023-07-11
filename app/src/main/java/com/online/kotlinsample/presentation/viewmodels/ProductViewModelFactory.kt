package com.online.kotlinsample.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.online.kotlinsample.domain.usecases.ProductDetailsUseCases
import com.online.kotlinsample.domain.usecases.ProductListUseCase

class ProductViewModelFactory(
    private val productListUseCase: ProductListUseCase,
    private val productDetailsUseCases: ProductDetailsUseCases
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductViewModel::class.java)){
            return ProductViewModel(productListUseCase, productDetailsUseCases) as T
        }
        throw java.lang.IllegalArgumentException("unknown View model")
    }

}