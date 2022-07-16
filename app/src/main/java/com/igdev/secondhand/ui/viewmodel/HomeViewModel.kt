package com.igdev.secondhand.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository):ViewModel(){
    private val _product :MutableLiveData<Resource<Response<List<AllProductResponse>>>> = MutableLiveData()
    val product :LiveData<Resource<Response<List<AllProductResponse>>>> get() = _product

    private val _category :MutableLiveData<Resource<List<CategoryResponseItem>>> = MutableLiveData()
    val category :LiveData<Resource<List<CategoryResponseItem>>> get() = _category

    private val _getAllSellerProduct = MutableLiveData<Resource<List<SellerProductResponseItem>>>()
    val getAllSellerProduct: LiveData<Resource<List<SellerProductResponseItem>>> get() = _getAllSellerProduct


    fun getProduct(status:String? = null,categoryId:String? = null,search:String?=null){
        viewModelScope.launch {
            _product.postValue(Resource.loading())
            try {
                _product.postValue(Resource.success(repository.getAllProduct(status,categoryId,search)))
            }catch (e:Exception){
                _product.postValue(Resource.error(e.localizedMessage?:"Error occurred"))
            }
        }
    }

    fun getSellerProduct(token: String) {
        viewModelScope.launch {
            _getAllSellerProduct.postValue(Resource.loading())
            try {
                _getAllSellerProduct.postValue(Resource.success(repository.getSellerProduct(token)))
            } catch (exception: Exception) {
                _getAllSellerProduct.postValue(Resource.error(exception.message ?: "Error"))
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