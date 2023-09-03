package com.online.kotlinsample.di

import com.online.kotlinsample.data.api.ServiceEndPoints
import com.online.kotlinsample.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideServiceEndPoints(client: OkHttpClient): ServiceEndPoints {
        return Retrofit.Builder()
            .baseUrl(Constants.END_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ServiceEndPoints::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(Constants.HTTP_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.HTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.HTTP_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        val interceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(Constants.HEADER_ACCEPT, Constants.HEADER_ACCEPT_JSON)
                .addHeader(Constants.HEADER_CACHE, Constants.HEADER_NO_CACHE)
                .addHeader(
                    Constants.HEADER_USER_AGENT,
                    System.getProperty(Constants.HEADER_HTTP) ?: ""
                )
                .build()
            chain.proceed(request)
        }
        return interceptor
    }
}