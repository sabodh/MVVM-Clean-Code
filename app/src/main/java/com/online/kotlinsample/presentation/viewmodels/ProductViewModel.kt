package com.online.kotlinsample.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.online.kotlinsample.data.api.UiState
import com.online.kotlinsample.data.model.Product
import com.online.kotlinsample.domain.usecases.ProductDetailsUseCases
import com.online.kotlinsample.domain.usecases.ProductListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor (
    val productListUseCase: ProductListUseCase,
    val productDetailsUseCases: ProductDetailsUseCases
) : ViewModel() {

    private var _productList = MutableLiveData<UiState<List<Product>>>()
    val productList get() = _productList
    private var _productDetails = MutableLiveData<UiState<Product>>()
    val productDetails get() = _productDetails

    init {
        getProductList()
    }

    fun getProductList() {
        viewModelScope.launch {
            _productList.postValue(UiState.Loading)
            val response = productListUseCase()
            _productList.postValue(response)
        }

    }

    fun getProductDetails(productId: String) {
        viewModelScope.launch {
            _productDetails.postValue(UiState.Loading)
            val result = productDetailsUseCases(productId)
            _productDetails.postValue(result)
        }

    }

}