package com.igdev.secondhand.service

import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.UpdateResponse
import com.igdev.secondhand.model.addProduct.SellProductResponse
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.model.detail.GetDetail
import com.igdev.secondhand.model.detail.PostOrderReq
import com.igdev.secondhand.model.detail.PostOrderResponse
import com.igdev.secondhand.model.getAuth.ResponseAuth
import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.login.LoginResponse
import com.igdev.secondhand.model.notification.NotifResponseItem
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.model.register.RegisterResponse
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.model.sellerproduct.SellerProductDetail
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.model.settingaccount.SettingAccountRequest
import com.igdev.secondhand.model.settingaccount.SettingAccountResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("buyer/product")
    suspend fun getAllProduct(
        @Query("status") status: String? = null,
        @Query("category_id") categoryId: String? = null,
        @Query("search") searchKeyword: String? = null
    ): Response<List<AllProductResponse>>
    @GET("seller/category")
    suspend fun getAllCategory(): List<CategoryResponseItem>
    @POST("auth/register")
    suspend fun postRegUser(@Body requestBody: RegistReq) : RegisterResponse
    @POST("auth/login")
    suspend fun postLogin(@Body requestBody: LoginReq) : LoginResponse
    @POST("buyer/order")
    suspend fun postBuyerOrder(@Header("access_token") token: String,@Body requestBody: PostOrderReq) : Response<PostOrderResponse>

    //anwar
    @Multipart
    @PUT("auth/user")
    suspend fun updateDataUser(
        @Header("access_token") token: String,
        @Part file: MultipartBody.Part?=null,
        @Part("full_name") name: RequestBody?,
        @Part("phone_number") phoneNumber: RequestBody?,
        @Part("address") address: RequestBody?,
        @Part("city") city: RequestBody?,
        @Part("email") email: RequestBody? = null,
        @Part("password") password: RequestBody? = null
    ): UpdateResponse

    //setting account
    @PUT("auth/change-password")
    suspend fun updatePassword(
        @Header("access_token") token: String,
        @Body requestBody:SettingAccountRequest
    ) : SettingAccountResponse


    @GET("auth/user")
    suspend fun getDataUser(@Header("access_token") token: String): ResponseAuth

    // notification
    @GET("notification")
    suspend fun GetNotif(@Header ("access_token") token: String ) : List<NotifResponseItem>
    //Seller Add Product
    @Multipart
    @POST("seller/product")
    suspend fun postProduct(
        @Header("access_token") token: String,
        @Part file: MultipartBody.Part,
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("base_price") base_price: RequestBody?,
        @Part("category_ids") categoryIds: List<Int>,
        @Part("location") location: RequestBody?,
    ): SellProductResponse

    //detail
    @GET("buyer/product/{id}")
    suspend fun getIdProduct(@Path("id") id:Int ) :GetDetail

    //detail seller
    @GET("seller/product/{id}")
    suspend fun getSellerProductDetail(@Header("access_token")token: String ,@Path("id") id:Int ) :SellerProductDetail

    //buyer history
    @GET("buyer/order")
    suspend fun getBuyerHistory(@Header ("access_token") token: String ): List<BuyerOrderResponse>

    //Seller product
    @GET("seller/product")
    suspend fun getSellerProduct(@Header ("access_token") token: String ): List<SellerProductResponseItem>
    //Seller Order
    @GET("seller/order")
    suspend fun getSellerOrder(@Header ("access_token") token: String ): List<SellerOrderResponseItem>


}