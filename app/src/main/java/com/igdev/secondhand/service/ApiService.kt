package com.igdev.secondhand.service

import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.login.LoginResponse
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.model.register.RegisterResponse
import retrofit2.http.*

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("buyer/product")
    suspend fun getAllProduct(
        @Query ("status") status:String,
        @Query("category_id") categoryId : String): List<AllProductResponse>
    @GET("seller/category")
    suspend fun getAllCategory(): List<CategoryResponseItem>
    @POST("auth/register")
    suspend fun postRegUser(@Body requestBody: RegistReq) : RegisterResponse
    @POST("auth/login")
    suspend fun postLogin(@Body requestBody: LoginReq) : LoginResponse
}