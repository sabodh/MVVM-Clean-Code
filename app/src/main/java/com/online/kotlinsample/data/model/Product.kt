package com.online.kotlinsample.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String,
    val quantity: Int? = 1
):Parcelable