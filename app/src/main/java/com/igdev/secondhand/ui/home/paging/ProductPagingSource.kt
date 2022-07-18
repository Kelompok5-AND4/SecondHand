package com.igdev.secondhand.ui.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.igdev.secondhand.model.PagingProduct.Data
import com.igdev.secondhand.repository.Repository
import com.igdev.secondhand.service.ApiService

class ProductPagingSource(private val apiService: ApiService):PagingSource<Int,Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val currentPage = params.key ?: 1
            val response = apiService.getProductAsBuyer()
            val responseData = mutableListOf<Data>()
            val data = response.body()!!.data ?: emptyList()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}