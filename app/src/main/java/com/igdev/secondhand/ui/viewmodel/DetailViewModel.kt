package com.igdev.secondhand.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.User
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.model.detail.GetDetail
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _detail : MutableLiveData<Resource<GetDetail>> = MutableLiveData()
    val detail : LiveData<Resource<GetDetail>> get() = _detail

    private val _getBuyerHistory = MutableLiveData<Resource<List<BuyerOrderResponse>>>()
    val getBuyerHistory: LiveData<Resource<List<BuyerOrderResponse>>> get() = _getBuyerHistory

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