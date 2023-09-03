package com.online.kotlinsample.di

import com.online.kotlinsample.data.api.ServiceEndPoints
import com.online.kotlinsample.data.repository.ProductRepositoryImpl
import com.online.kotlinsample.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideProductRepository(
        serviceEndPoints: ServiceEndPoints): ProductRepository {
        return ProductRepositoryImpl(serviceEndPoints)
    }
}