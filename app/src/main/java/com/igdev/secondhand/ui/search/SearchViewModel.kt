package com.igdev.secondhand.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.igdev.secondhand.database.SearchHistoryEntity
import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository):ViewModel() {

    //Search List Section
    private val _searchHistory = MutableLiveData<Resource<List<SearchHistoryEntity>>>()
    val searchHistory : LiveData<Resource<List<SearchHistoryEntity>>> get() = _searchHistory

    fun getSearchHistory() {
        viewModelScope.launch {
            _searchHistory.postValue(Resource.loading())
            try {
                val searchData = Resource.success(repository.getSearchHistory())
                _searchHistory.postValue(searchData)
            } catch (exp: Exception) {
                _searchHistory.postValue(
                    Resource.error(
                        exp.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }

    fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity){
        viewModelScope.launch {
            repository.insertSearchHistory(searchHistoryEntity)
        }
    }

    //Search Result Section

    private val _searchResponse = MutableLiveData<Resource<Response<List<AllProductResponse>>>>()
    val searchResponse : LiveData<Resource<Response<List<AllProductResponse>>>> get() = _searchResponse

    fun getProduct(
        status: String? = null,
        categoryId: String? = null,
        searchKeyword: String? = null
    ) {
        viewModelScope.launch {
            _searchResponse.postValue(Resource.loading())
            try {
                val dataExample = Resource.success(repository.getAllProduct(
                    status,
                    categoryId,
                    searchKeyword
                ))
                _searchResponse.postValue(dataExample)
            } catch (exp: Exception) {
                _searchResponse.postValue(
                    Resource.error(
                        exp.localizedMessage ?: "Error occured"
                    )
                )
            }
        }
    }
}