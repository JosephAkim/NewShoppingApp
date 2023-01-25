package com.kim.newshoppingapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.kim.newshoppingapp.data.Product
import com.kim.newshoppingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainCategoryViewModel @Inject constructor(
    private val fireStore: FirebaseFirestore
): ViewModel() {

    private val _specialProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val specialProducts : StateFlow<Resource<List<Product>>> = _specialProducts

    private val _bestDealsProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestDealsProducts : StateFlow<Resource<List<Product>>> = _bestDealsProducts

    private val _bestProducts = MutableStateFlow<Resource<List<Product>>>(Resource.Unspecified())
    val bestProducts : StateFlow<Resource<List<Product>>> = _bestProducts

    init {
        fetchSpecialProducts()
        fetchBestProducts()
        fetchBestDealsProducts()
    }

    fun fetchSpecialProducts(){
        viewModelScope.launch {
            _specialProducts.emit(Resource.Loading())
        }
        fireStore.collection("Products")
            .whereEqualTo("Category", "Special Products").get().addOnSuccessListener { result ->
                val specialProductList = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Success(specialProductList))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _specialProducts.emit(Resource.Failure(it.message.toString()))
                }
            }
    }

    fun fetchBestDealsProducts(){
        viewModelScope.launch {
            _bestDealsProducts.emit(Resource.Loading())
        }
        fireStore.collection("Products")
            .whereEqualTo("Category", "Best Deals").get().addOnSuccessListener { result ->
                val bestDealsProductList = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestDealsProducts.emit(Resource.Success(bestDealsProductList))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestDealsProducts.emit(Resource.Failure(it.message.toString()))
                }
            }
    }

    fun fetchBestProducts(){
        viewModelScope.launch {
            _bestProducts.emit(Resource.Loading())
        }
        fireStore.collection("Products").get()
            .addOnSuccessListener { result ->
                val bestProductList = result.toObjects(Product::class.java)
                viewModelScope.launch {
                    _bestProducts.emit(Resource.Success(bestProductList))
                }

            }.addOnFailureListener {
                viewModelScope.launch {
                    _bestProducts.emit(Resource.Failure(it.message.toString()))
                }
            }
    }
}