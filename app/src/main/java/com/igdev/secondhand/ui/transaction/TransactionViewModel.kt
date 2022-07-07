package com.igdev.secondhand.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.User
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.model.notification.NotifResponseItem
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val repository: Repository) :ViewModel() {

    private val _getBuyerHistory = MutableLiveData<Resource<List<BuyerOrderResponse>>>()
    val getBuyerHistory: LiveData<Resource<List<BuyerOrderResponse>>> get() = _getBuyerHistory

    private val _getAllSellerProduct = MutableLiveData<Resource<List<SellerProductResponseItem>>>()
    val getAllSellerProduct: LiveData<Resource<List<SellerProductResponseItem>>> get() = _getAllSellerProduct

    private val _getAllSellerOrder = MutableLiveData<Resource<List<SellerOrderResponseItem>>>()
    val getAllSellerOrder: LiveData<Resource<List<SellerOrderResponseItem>>> get() = _getAllSellerOrder

    private val _getToken = MutableLiveData<User>()
    val getToken: LiveData<User> get() = _getToken

    fun getBuyerHistory(token: String) {
        viewModelScope.launch {
            _getBuyerHistory.postValue(Resource.loading())
            try {
                _getBuyerHistory.postValue(Resource.success(repository.getBuyerHistory(token)))
            } catch (exception: Exception) {
                _getBuyerHistory.postValue(Resource.error(exception.message ?: "Error"))
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