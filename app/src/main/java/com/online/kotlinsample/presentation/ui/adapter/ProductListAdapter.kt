package com.online.kotlinsample.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.online.kotlinsample.data.model.Product
import com.online.kotlinsample.databinding.RowProductBinding
import com.online.kotlinsample.utils.ImageUtils

class ProductListAdapter(
    private val onProductItemClick: (Product) -> Unit,
    private val imageUtils: ImageUtils
) : ListAdapter<Product,
        ProductListAdapter.ProductViewHolder>(ComparatorDiffUtil()) {

    inner class ProductViewHolder(private val binding: RowProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(product: Product) {
            with(product) {
                binding.apply {
                    txtProductName.text = title
                    txtPrice.text = "£${price}"
                    txtCategory.text = category
                    imageUtils.loadImage(imageUrl = image, imageView)
                    root.setOnClickListener {
                        onProductItemClick(product)
                    }
                }
            }
        }


    }

    class ComparatorDiffUtil() : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = RowProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bindItem(product)
    }
}