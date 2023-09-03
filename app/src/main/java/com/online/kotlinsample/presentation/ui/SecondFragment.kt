package com.online.kotlinsample.presentation.ui

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.online.kotlinsample.data.api.UiState
import com.online.kotlinsample.data.model.Product
import com.online.kotlinsample.databinding.FragmentSecondBinding
import com.online.kotlinsample.presentation.viewmodels.ProductViewModel
import com.online.kotlinsample.utils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var imageUtils: ImageUtils

    private val productViewModel by viewModels<ProductViewModel>()

    private val args: SecondFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeProduct()
        // handle date received through arguments
        binding.apply {
            args.selectedProduct.apply {
                setProductDetails(binding, this)
                productViewModel.getProductDetails(this.id.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * Setup selected product details initially
     */
    fun setProductDetails(
        fragmentProductDetailsBinding: FragmentSecondBinding,
        product: Product
    ) {
        fragmentProductDetailsBinding.apply {
            txtAmount.text = "£" + product.price.toString()
            txtCategory.text = product.category.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(
                    Locale.ROOT
                ) else it.toString()
            }
            txtSummary.text = product.description
            txtInfoHeader.text = product.title
//            product.rating.rate.toString()+ "/ " +product.rating.count.toString()
            imageUtils.loadImage(product.image, imageView)
            (activity as? MainActivity)?.setHeader(
                product.title,
                Gravity.START,
                View.VISIBLE
            )
        }

    }

    /**
     * Setup observer for product details received from server
     */
    private fun observeProduct() {
        val observer = Observer<UiState<Product>> {
            it
            when (it) {
                is UiState.Success -> {
                    setProductDetails(binding, it.data!!)
                    showProgress(View.GONE)
                }
                is UiState.Error -> {
                    showProgress(View.GONE)
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is UiState.Loading -> {
                    showProgress(View.VISIBLE)
                }
            }
        }
        productViewModel.productDetails.observe(viewLifecycleOwner, observer)
    }

    private fun showProgress(status: Int) {
        binding.progressBar.visibility = status
    }
}