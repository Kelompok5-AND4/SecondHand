package com.igdev.secondhand.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository):ViewModel(){
    private val _product :MutableLiveData<Resource<List<AllProductResponse>>> = MutableLiveData()
    val product :LiveData<Resource<List<AllProductResponse>>> get() = _product

    private val _category :MutableLiveData<Resource<List<CategoryResponseItem>>> = MutableLiveData()
    val category :LiveData<Resource<List<CategoryResponseItem>>> get() = _category


    fun getProduct(status:String,categoryId:String){
        viewModelScope.launch {
            _product.postValue(Resource.loading())
            try {
                _product.postValue(Resource.success(repository.getAllProduct(status,categoryId)))
            }catch (e:Exception){
                _product.postValue(Resource.error(e.localizedMessage?:"Error occurred"))
            }
        }
    }

    fun getCategory(){
        viewModelScope.launch {
            _category.postValue(Resource.loading())
            try {
                _category.postValue(Resource.success(repository.getAllCategory()))
            }catch (e:Exception){
                _category.postValue(Resource.error(e.localizedMessage?:"Error occurred"))
            }
        }
    }
}