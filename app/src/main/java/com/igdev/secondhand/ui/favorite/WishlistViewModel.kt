package com.igdev.secondhand.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.wishlist.GetWishlistById
import com.igdev.secondhand.model.wishlist.GetWishlistResponse
import com.igdev.secondhand.model.wishlist.PostWishListResponse
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _getAllWishlist : MutableLiveData<List<GetWishlistResponse>> = MutableLiveData()
    val getAllWishlist :LiveData<List<GetWishlistResponse>> get() = _getAllWishlist

    private val _getWishlistById : MutableLiveData<List<GetWishlistById>> = MutableLiveData()
    val getWishlistById : LiveData<List<GetWishlistById>> get() = _getWishlistById

    private val _postWishList : MutableLiveData<PostWishListResponse> = MutableLiveData()
    val postWishlist : LiveData<PostWishListResponse> get() = _postWishList


    fun getAllWishlistby (token: String){
        viewModelScope.launch {
            _getAllWishlist.postValue(repository.getWishlist(token))
        }
    }
}