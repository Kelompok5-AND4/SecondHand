package com.IGDev.secondhand.service

import com.IGDev.secondhand.model.AllProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("buyer/product")
    suspend fun getAllProduct(): AllProductResponse

}