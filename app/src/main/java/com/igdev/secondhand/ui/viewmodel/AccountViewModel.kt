package com.igdev.secondhand.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.User
import com.igdev.secondhand.model.getAuth.ResponseAuth
import com.igdev.secondhand.model.settingaccount.SettingAccountRequest
import com.igdev.secondhand.model.settingaccount.SettingAccountResponse
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(private val repository: Repository):ViewModel(){
    private var _user = MutableLiveData<Resource<ResponseAuth>>()
    val user : LiveData<Resource<ResponseAuth>> get() = _user

    private val _getToken = MutableLiveData<User>()
    val getToken: LiveData<User> get() = _getToken

    private val _setAccount = MutableLiveData<Resource<SettingAccountResponse>> ()
    val setAccount : LiveData<Resource<SettingAccountResponse>> get() = _setAccount

    fun getToken() {
        viewModelScope.launch {
            repository.getDatastore().collect {
                _getToken.postValue(it)
            }
        }
    }
    fun getUserData(token: String){
        viewModelScope.launch {
            _user.postValue(Resource.loading())
            try {
                _user.postValue(Resource.success(repository.getDataUser(token)))
            }catch (e: Exception){
                _user.postValue(Resource.error(e.localizedMessage?: "Error Occured"))
            }
        }
    }

    fun deleteToken(){
        viewModelScope.launch {
            repository.deleteToken()
        }
    }

    //setting account
    fun settingAccount(token: String, requestBody : SettingAccountRequest){
        viewModelScope.launch {
            _setAccount.postValue(Resource.loading())
            try {
                _setAccount.postValue(Resource.success(repository.settingAccount(token, requestBody)))
            }catch (exception : Exception){
                _setAccount.postValue(Resource.error(exception.message?: "Error Occured"))
            }
        }
    }
}