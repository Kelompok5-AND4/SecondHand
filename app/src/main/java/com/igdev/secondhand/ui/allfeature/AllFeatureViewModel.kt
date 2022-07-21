package com.igdev.secondhand.ui.allfeature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllFeatureViewModel @Inject constructor(private val repository: Repository):ViewModel() {
    private val _category : MutableLiveData<Resource<List<CategoryResponseItem>>> = MutableLiveData()
    val category : LiveData<Resource<List<CategoryResponseItem>>> get() = _category

    fun getCategory(){
        viewModelScope.launch {
            _category.postValue(Resource.loading())
            try {
                _category.postValue(Resource.success(repository.getAllCategory()))
            }catch (e:Exception){
                _category.postValue(Resource.error(e.localizedMessage?:"Error occurred"))
            }
        }
    }
}