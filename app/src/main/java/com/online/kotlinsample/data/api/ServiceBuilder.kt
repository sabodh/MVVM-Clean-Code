package com.online.kotlinsample.data.api

import com.online.kotlinsample.utils.Constants.END_URL
import com.online.kotlinsample.utils.Constants.HEADER_ACCEPT
import com.online.kotlinsample.utils.Constants.HEADER_ACCEPT_JSON
import com.online.kotlinsample.utils.Constants.HEADER_CACHE
import com.online.kotlinsample.utils.Constants.HEADER_HTTP
import com.online.kotlinsample.utils.Constants.HEADER_NO_CACHE
import com.online.kotlinsample.utils.Constants.HEADER_USER_AGENT
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val interceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader(HEADER_ACCEPT, HEADER_ACCEPT_JSON)
            .addHeader(HEADER_CACHE, HEADER_NO_CACHE)
            .addHeader(HEADER_USER_AGENT, System.getProperty(HEADER_HTTP) ?: "")
            .build()
        chain.proceed(request)

    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(END_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun <T> buildService(service : Class<T>):T {
        return retrofit.create(service)
    }

}