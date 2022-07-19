package com.igdev.secondhand.ui.wishlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.User
import com.igdev.secondhand.model.wishlist.GetWishlistById
import com.igdev.secondhand.model.wishlist.GetWishlistResponse
import com.igdev.secondhand.model.wishlist.PostWishListResponse
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _getAllWishlist : MutableLiveData<Resource<List<GetWishlistResponse>>> = MutableLiveData()
    val getAllWishlist :LiveData<Resource<List<GetWishlistResponse>>> get() = _getAllWishlist

    private val _getToken = MutableLiveData<User>()
    val getToken: LiveData<User> get() = _getToken



    fun getToken() {
        viewModelScope.launch {
            repository.getDatastore().collect {
                _getToken.postValue(it)
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
}