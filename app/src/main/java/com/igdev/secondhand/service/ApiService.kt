package com.igdev.secondhand.service

import com.igdev.secondhand.model.AllProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("buyer/product")
    suspend fun getAllProduct(): AllProductResponse

}