package com.igdev.secondhand.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.login.LoginResponse
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _userLogin = MutableLiveData<Resource<LoginResponse>>()
    val userLogin: LiveData<Resource<LoginResponse>>
        get() = _userLogin

    fun loginPost(requestBody : LoginReq) {
        viewModelScope.launch {
            _userLogin.postValue(Resource.loading())
            try {
                _userLogin.postValue(Resource.success(repository.postLogin(requestBody)))
            } catch (exception: Exception) {
                _userLogin.postValue(Resource.error(exception.message ?: "Error"))
            }
        }
    }

}