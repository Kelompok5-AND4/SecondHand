package com.igdev.secondhand.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.register.RegisterResponse
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _userRegister = MutableLiveData<Resource<RegisterResponse>>()
    val userRegister: LiveData<Resource<RegisterResponse>>
        get() = _userRegister

    fun registerPost(requestBody : RegistReq) {
        viewModelScope.launch {
            _userRegister.postValue(Resource.loading())
            try {
                _userRegister.postValue(Resource.success(repository.postRegUser(requestBody)))
            } catch (exception: Exception) {
                _userRegister.postValue(Resource.error(exception.message ?: "Error"))
            }
        }
    }
}