package com.igdev.secondhand.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.User
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.model.detail.GetDetail
import com.igdev.secondhand.model.detail.PostOrderReq
import com.igdev.secondhand.model.detail.PostOrderResponse
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.model.wishlist.*
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _detail : MutableLiveData<Resource<GetDetail>> = MutableLiveData()
    val detail : LiveData<Resource<GetDetail>> get() = _detail

    private val _getBuyerHistory = MutableLiveData<Resource<List<BuyerOrderResponse>>>()
    val getBuyerHistory: LiveData<Resource<List<BuyerOrderResponse>>> get() = _getBuyerHistory

    private val _postOrder = MutableLiveData<Resource<Response<PostOrderResponse>>>()
    val postOrder : LiveData<Resource<Response<PostOrderResponse>>> get()= _postOrder

    //wishlist
    private val _postWishList : MutableLiveData<Resource<PostWishListResponse>> = MutableLiveData()
    val postWishlist : LiveData<Resource<PostWishListResponse>> get() = _postWishList

    private val _getAllWishlist : MutableLiveData<Resource<List<GetWishlistResponse>>> = MutableLiveData()
    val getAllWishlist :LiveData<Resource<List<GetWishlistResponse>>> get() = _getAllWishlist

    private val _deleteWishlist : MutableLiveData<Resource<DeleteWishlistResponse>> = MutableLiveData()
    val deleteWishlist : LiveData<Resource<DeleteWishlistResponse>> get() = _deleteWishlist



    //wishlist
    fun postWishlist(token: String, requestBody : PostWishlistRequest){
        viewModelScope.launch {
            _postWishList.postValue(Resource.loading())
            try {
                _postWishList.postValue(Resource.success(repository.postWishlist(token,requestBody)))
            }catch (exception : Exception){
                _postWishList.postValue(Resource.error(exception.message ?: "Error"))
            }
        }
    }

    fun deleteWishlist(token: String, id: Int){
        viewModelScope.launch {
            _deleteWishlist.postValue(Resource.loading())
            try {
                _deleteWishlist.postValue(Resource.success(repository.deleteWishlist(token, id)))
            }catch (exception : Exception){
                _deleteWishlist.postValue(Resource.error(exception.message ?: "Error"))
            }
        }
    }

    fun getAllWishlist (token: String){
        viewModelScope.launch {
            _getAllWishlist.postValue(Resource.loading())
            try {
                _getAllWishlist.postValue(Resource.success(repository.getWishlist(token)))
            }catch (exception : Exception){
                _getAllWishlist.postValue(Resource.error(exception.message ?: "Error"))
            }
        }
    }




    fun postOrder(token: String,requestBody : PostOrderReq) {
        viewModelScope.launch {
            _postOrder.postValue(Resource.loading())
            try {
                _postOrder.postValue(Resource.success(repository.postOrder(token,requestBody)))
            } catch (exception: Exception) {
                _postOrder.postValue(Resource.error(exception.message ?: "Error"))
            }
        }
    }

    fun getDetail(id:Int){
        viewModelScope.launch {
            _detail.postValue(Resource.loading())
            try {
                _detail.postValue(Resource.success(repository.getDetail(id)))
            }catch (exception : Exception){
                _detail.postValue(Resource.error(exception.localizedMessage?:"Error"))
            }
        }
    }
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
    fun getToken() {
        viewModelScope.launch {
            repository.getDatastore().collect {
                _getToken.postValue(it)
            }
        }
    }
}