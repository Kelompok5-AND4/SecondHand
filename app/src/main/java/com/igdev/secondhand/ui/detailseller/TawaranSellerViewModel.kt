package com.igdev.secondhand.ui.detailseller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.User
import com.igdev.secondhand.model.detail.GetDetail
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.model.sellerproduct.SellerProductDetail
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TawaranSellerViewModel @Inject constructor(private val repository: Repository) :ViewModel() {
    private val _getAllSellerOrder = MutableLiveData<Resource<List<SellerOrderResponseItem>>>()
    val getAllSellerOrder: LiveData<Resource<List<SellerOrderResponseItem>>> get() = _getAllSellerOrder

    private val _getToken = MutableLiveData<User>()
    val getToken: LiveData<User> get() = _getToken
    private val _detail : MutableLiveData<Resource<SellerProductDetail>> = MutableLiveData()
    val detail : LiveData<Resource<SellerProductDetail>> get() = _detail

    fun getDetail(token: String,id:Int){
        viewModelScope.launch {
            _detail.postValue(Resource.loading())
            try {
                _detail.postValue(Resource.success(repository.getSellerDetailProduct(token,id)))
            }catch (exception : Exception){
                _detail.postValue(Resource.error(exception.localizedMessage?:"Error"))
            }
        }
    }
    fun getSellerOrder(token: String) {
        viewModelScope.launch {
            _getAllSellerOrder.postValue(Resource.loading())
            try {
                _getAllSellerOrder.postValue(Resource.success(repository.getSellerOrder(token)))
            } catch (exception: Exception) {
                _getAllSellerOrder.postValue(Resource.error(exception.message ?: "Error"))
            }
        }
    }

    fun getToken() {
        viewModelScope.launch {
            repository.getDatastore().collect {
                _getToken.postValue(it)
            }
        }
    }
}