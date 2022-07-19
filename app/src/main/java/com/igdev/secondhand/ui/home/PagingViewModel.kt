package com.igdev.secondhand.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.igdev.secondhand.model.PagingProduct.Product
import com.igdev.secondhand.model.Resource
import com.igdev.secondhand.repository.Repository
import com.igdev.secondhand.service.ApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(private val repository: Repository) :ViewModel() {

    fun getProducts(categoryId: Int? =null): Flow<PagingData<UiModel.ProductItem>> =
        repository.getProductStream(categoryId)
            .map { pagingData -> pagingData.map { UiModel.ProductItem(it) } }
            .cachedIn(viewModelScope)

    private val _getHomeProductResponse =
        MutableLiveData<Resource<Response<Product>>>()
}
sealed class UiAction {
    data class Scroll(val position: Int) : UiAction()
}

data class UiState(
    val hasNotScrolledForCurrentState: Boolean = false
)

sealed class UiModel {
    data class ProductItem(val products: Product) : UiModel()
}