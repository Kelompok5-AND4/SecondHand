package com.igdev.secondhand.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.igdev.secondhand.service.ApiService
import com.igdev.secondhand.ui.home.paging.ProductPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PagingViewModel @Inject constructor(private val apiService: ApiService) :ViewModel() {

    val listData = Pager(PagingConfig(1)){
        ProductPagingSource(apiService)
    }.flow.cachedIn(viewModelScope)
}