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

    init {
        fetchSpecialProducts()
    }

    fun fetchSpecialProducts(){
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
}