package com.igdev.secondhand.service

import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.login.LoginResponse
import com.igdev.secondhand.model.notification.NotifResponseItem
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.model.register.RegisterResponse
import retrofit2.http.*

import retrofit2.http.GET

interface ApiService {
    @GET("buyer/product")
    suspend fun getAllProduct(): AllProductResponse
    @POST("auth/register")
    suspend fun postRegUser(@Body requestBody: RegistReq) : RegisterResponse
    @POST("auth/login")
    suspend fun postLogin(@Body requestBody: LoginReq) : LoginResponse
    // notification
    @GET("notification")
    suspend fun GetNotif(@Header ("access_token") token: String ) : List<NotifResponseItem>
}