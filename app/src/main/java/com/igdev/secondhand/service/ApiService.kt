package com.igdev.secondhand.service

import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.RegistResponse
import com.igdev.secondhand.model.RegisterReq
import retrofit2.http.*

interface ApiService {
    @GET("buyer/product")
    suspend fun getAllProduct(): AllProductResponse
    @POST("auth/register")
    suspend fun postUser(@Body requestBody:RegisterReq) : RegistResponse

}