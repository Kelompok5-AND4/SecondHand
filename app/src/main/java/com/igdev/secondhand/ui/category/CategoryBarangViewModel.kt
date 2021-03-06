package com.igdev.secondhand.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class CategoryBarangViewModel @Inject constructor(private val repository: Repository) :ViewModel()  {

    private val _category :MutableLiveData<Resource<Response<List<AllProductResponse>>>> = MutableLiveData()
    val category :LiveData<Resource<Response<List<AllProductResponse>>>> get() = _category

    fun getProduct(status:String? = null,categoryId:String? = null,search:String?=null){
        viewModelScope.launch {
            _category.postValue(Resource.loading())
            try {
                _category.postValue(Resource.success(repository.getAllProduct(status,categoryId,search)))
            }catch (e:Exception){
                _category.postValue(Resource.error(e.localizedMessage?:"Error occurred"))
            }
        }
    }


}