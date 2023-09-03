package com.online.kotlinsample.di

import android.app.Application
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlideModule {

    @Provides
    @Singleton
    fun provideGlide(application: Application): RequestManager {
        return Glide.with(application)
    }
}