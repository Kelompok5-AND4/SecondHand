package com.igdev.secondhand.service

import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.UpdateResponse
import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.login.LoginResponse
import com.igdev.secondhand.model.notification.NotifResponseItem
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.model.register.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    //anwar
    @Multipart
    @PUT("auth/update")
    suspend fun update(@Header("Authorization") token: String,
                       @Part("username") username: RequestBody,
                       @Part("email") email: RequestBody,
                       @Part file: MultipartBody.Part) : UpdateResponse
    // notification
    @GET("notification")
    suspend fun GetNotif(@Header ("access_token") token: String ) : List<NotifResponseItem>

}