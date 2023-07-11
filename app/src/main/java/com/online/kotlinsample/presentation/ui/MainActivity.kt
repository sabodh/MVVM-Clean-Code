package com.online.kotlinsample.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.online.kotlinsample.data.api.ServiceBuilder
import com.online.kotlinsample.data.api.ServiceEndPoints
import com.online.kotlinsample.data.api.UiState
import com.online.kotlinsample.data.model.Product
import com.online.kotlinsample.data.repository.ProductRepositoryImpl
import com.online.kotlinsample.databinding.ActivityMainBinding
import com.online.kotlinsample.domain.usecases.ProductDetailsUseCases
import com.online.kotlinsample.domain.usecases.ProductListUseCase
import com.online.kotlinsample.presentation.viewmodels.ProductViewModel
import com.online.kotlinsample.presentation.viewmodels.ProductViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val serviceBuilder = ServiceBuilder.buildService(ServiceEndPoints::class.java)
        val productRepository = ProductRepositoryImpl(serviceBuilder)
        val productListUseCase = ProductListUseCase(productRepository)
        val productDetailsUseCases = ProductDetailsUseCases(productRepository)
        val productViewModelFactory = ProductViewModelFactory(productListUseCase, productDetailsUseCases)
        val productViewModel = ViewModelProvider(this, productViewModelFactory)
            .get(ProductViewModel::class.java)

        val observer = Observer<UiState<List<Product>>>{ state->
            when(state){
                is UiState.Loading->{
                    Log.e("result","Loading");
                }
                is UiState.Error -> {
                    Log.e("result","Error");
                }
                is UiState.Success -> {
                    Log.e("result","Success ${state.data.size}");
                }
            }
        }
        productViewModel.productList.observe(this, observer)
    }


}