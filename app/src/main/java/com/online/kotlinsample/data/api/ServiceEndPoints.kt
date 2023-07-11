package com.online.kotlinsample.data.api
import com.online.kotlinsample.data.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceEndPoints {

    @GET("/products")
    suspend fun getProductList(): Response<List<Product>>

    @GET("/products/{productId}")
    suspend fun getProductDetails(@Path("productId") productId: String) : Response<Product>
}