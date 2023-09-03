package com.online.kotlinsample.utils

import android.widget.ImageView
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class ImageUtils @Inject constructor(private val glide: RequestManager) {
    fun loadImage(imageUrl: String, view: ImageView) {
        glide.load(imageUrl)
            .into(view)
    }

}