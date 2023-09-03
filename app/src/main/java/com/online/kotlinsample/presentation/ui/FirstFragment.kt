package com.online.kotlinsample.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.online.kotlinsample.data.api.UiState
import com.online.kotlinsample.data.model.Product
import com.online.kotlinsample.databinding.FragmentFirstBinding
import com.online.kotlinsample.presentation.ui.adapter.ProductListAdapter
import com.online.kotlinsample.presentation.viewmodels.ProductViewModel
import com.online.kotlinsample.utils.ImageUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val productViewModel : ProductViewModel by viewModels()

    @Inject
    lateinit var imageUtils: ImageUtils

    private lateinit var productAdapter: ProductListAdapter

    private val spanCount = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerview(view)
        swipeRefreshing()
        observeProducts()
//        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    private fun observeProducts() {
        val observer = Observer<UiState<List<Product>>>{ state->
            when(state){
                is UiState.Loading->{
                    showProgress(View.VISIBLE)
                    showRecyclerview(View.VISIBLE)
                    showError(View.GONE)
                    showPullToRefresh(false)
                }
                is UiState.Error -> {
                    showProgress(View.GONE)
                    showRecyclerview(View.GONE)
                    showError(View.VISIBLE)
                    showPullToRefresh(false)
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
                is UiState.Success -> {
                    productAdapter.submitList(state.data)
                    showProgress(View.GONE)
                    showError(View.GONE)
                    showRecyclerview(View.VISIBLE)
                    showPullToRefresh(false)
                }
            }
        }
        productViewModel.productList.observe(viewLifecycleOwner, observer)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initRecyclerview(view: View){
        binding.recyclerView.layoutManager =
            GridLayoutManager(view.context, spanCount)
        productAdapter = ProductListAdapter(::onProductItemClick, imageUtils)
        binding.recyclerView.adapter = productAdapter
    }
    private fun onProductItemClick(product: Product){
        val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(product)
        Navigation.findNavController(_binding!!.root).navigate(action)
    }
    private fun showProgress(status: Int){
        binding.progressBar.visibility = status
    }
    private fun showRecyclerview(status: Int){
        binding.recyclerView.visibility = status
    }
    private fun showError(status: Int){
        binding.textviewEmpty.visibility = status
    }
    private fun swipeRefreshing(){
        binding.swipeRefresh.setOnRefreshListener {
            showPullToRefresh(true)
            productViewModel.getProductList()
        }
    }
    private fun showPullToRefresh(visibility: Boolean) {
        binding.swipeRefresh.isRefreshing = visibility
    }
}