package com.online.kotlinsample.data.repository

import com.online.kotlinsample.data.api.ServiceEndPoints
import com.online.kotlinsample.data.api.UiState
import com.online.kotlinsample.data.model.Product
import com.online.kotlinsample.domain.repository.ProductRepository
import java.net.UnknownHostException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val serviceEndPoints: ServiceEndPoints) : ProductRepository {
    override suspend fun getProductList(): UiState<List<Product>> {
        return try {
            val response = serviceEndPoints.getProductList()
            if (response.isSuccessful) {
                response.body()?.let {
                    UiState.Success(it)
                } ?: UiState.Error("Unknown error")
            } else {
                UiState.Error(response.errorBody()?.string() ?: "Unknown Error")
            }
        } catch (e: UnknownHostException) {
            UiState.Error("Network Connection Lost!")
        } catch (e: Exception) {
            UiState.Error(e.message.toString())
        }
    }

    override suspend fun getProductDetails(productId: String): UiState<Product> {
        return try {
            val response = serviceEndPoints.getProductDetails(productId)
            if (response.isSuccessful) {
                response.body()?.let {
                    UiState.Success(it)
                } ?: UiState.Error("Unknown error")
            } else {
                UiState.Error(response.errorBody()?.string() ?: "Unknown Error")
            }
        } catch (e: UnknownHostException) {
            UiState.Error("Network Connection Lost!")
        } catch (e: Exception) {
            UiState.Error(e.message.toString())
        }
    }

}