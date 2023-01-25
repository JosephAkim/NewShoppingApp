package com.kim.newshoppingapp.fragment.categories

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kim.newshoppingapp.R
import com.kim.newshoppingapp.adapters.BestDealsAdapter
import com.kim.newshoppingapp.adapters.BestProductAdapter
import com.kim.newshoppingapp.adapters.SpecialProductsAdapter
import com.kim.newshoppingapp.databinding.FragmentMainCategoryBinding
import com.kim.newshoppingapp.util.Resource
import com.kim.newshoppingapp.viewmodel.MainCategoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

private val TAG = "MainCategoryFragment"
@AndroidEntryPoint
class MainCategoryFragment: Fragment(R.layout.fragment_main_category) {
    private lateinit var binding: FragmentMainCategoryBinding
    private lateinit var specialProductsAdapter: SpecialProductsAdapter
    private lateinit var bestDealsAdapter: BestDealsAdapter
    private lateinit var bestProductAdapter: BestProductAdapter
    private val viewModel by viewModels<MainCategoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainCategoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpSpecialProductRv()
        setUpBestDealsRv()
        setUpBestProductsRv()
        lifecycleScope.launchWhenStarted {
            viewModel.specialProducts.collectLatest {
                when(it){
                    is Resource.Loading ->{
                        showLoading()
                    }
                    is Resource.Success ->{
                        specialProductsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Failure ->{
                        hideLoading()
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bestDealsProducts.collectLatest {
                when(it){
                    is Resource.Loading ->{
                        showLoading()
                    }
                    is Resource.Success ->{
                        bestDealsAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Failure ->{
                        hideLoading()
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                    }
                    else -> Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.bestProducts.collectLatest {
                when(it){
                    is Resource.Loading ->{
                        showLoading()
                    }
                    is Resource.Success ->{
                        bestProductAdapter.differ.submitList(it.data)
                        hideLoading()
                    }
                    is Resource.Failure ->{
                        hideLoading()
                        Log.e(TAG, it.message.toString())
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                    }
                    else -> Unit
                }
            }
        }
    }

    private fun setUpBestProductsRv() {
        bestProductAdapter = BestProductAdapter()
        binding.rvBestProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL,false)
            adapter = bestProductAdapter
        }
    }

    private fun setUpBestDealsRv() {
        bestDealsAdapter = BestDealsAdapter()
        binding.rvBestDealsProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = bestDealsAdapter
        }
    }

    private fun hideLoading() {
        binding.mainCategoryProgressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.mainCategoryProgressBar.visibility = View.VISIBLE
    }

    private fun setUpSpecialProductRv() {
        specialProductsAdapter = SpecialProductsAdapter()
        binding.rvSpecialProducts.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = specialProductsAdapter
        }
    }
}