package com.igdev.secondhand.ui.detailseller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.User
import com.igdev.secondhand.model.detail.GetDetail
import com.igdev.secondhand.model.sellerorder.PatchSellerOrderReq
import com.igdev.secondhand.model.sellerorder.PatchSellerOrderResponse
import com.igdev.secondhand.model.sellerorder.SellerOrderIdResponse
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.model.sellerproduct.SellerProductDetail
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
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
    private val _bidder = MutableLiveData<Resource<Response<SellerOrderIdResponse>>>()
    val bidder: LiveData<Resource<Response<SellerOrderIdResponse>>> get() = _bidder

    fun bidder(token: String,id:Int) {
        viewModelScope.launch {
            _bidder.postValue(Resource.loading())
            try {
                val response = Resource.success(repository.getSellerOrderId(token,id))
                _bidder.postValue(response)
            } catch (t: Throwable) {
                _bidder.postValue(Resource.error(t.message))
            }
        }
    }

    private val _status = MutableLiveData<Resource<Response<PatchSellerOrderResponse>>>()
    val status: LiveData<Resource<Response<PatchSellerOrderResponse>>> get() = _status

    fun statusItem(token: String,id:Int,request: PatchSellerOrderReq) {
        viewModelScope.launch {
            _status.postValue(Resource.loading())
            try {
                val response = Resource.success(repository.patchSellerOrderId(token,id,request))
                _status.postValue(response)
            } catch (t: Throwable) {
                _status.postValue(Resource.error(t.message))
            }
        }
    }
}