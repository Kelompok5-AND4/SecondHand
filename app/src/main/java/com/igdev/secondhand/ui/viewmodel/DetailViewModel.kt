package com.igdev.secondhand.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.model.detail.GetDetail
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _detail : MutableLiveData<Resource<GetDetail>> = MutableLiveData()
    val detail : LiveData<Resource<GetDetail>> get() = _detail

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
}