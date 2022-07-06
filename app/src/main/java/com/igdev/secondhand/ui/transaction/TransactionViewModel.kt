package com.igdev.secondhand.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.User
import com.igdev.secondhand.model.notification.NotifResponseItem
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(private val repository: Repository) :ViewModel() {

    private val _getNotif = MutableLiveData<Resource<List<NotifResponseItem>>>()
    val getNotif: LiveData<Resource<List<NotifResponseItem>>> get() = _getNotif

    private val _getToken = MutableLiveData<User>()
    val getToken: LiveData<User> get() = _getToken

    fun getAllNotif(token: String) {
        viewModelScope.launch {
            _getNotif.postValue(Resource.loading())
            try {
                _getNotif.postValue(Resource.success(repository.getNotif(token)))
            } catch (exception: Exception) {
                _getNotif.postValue(Resource.error(exception.message ?: "Error"))
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