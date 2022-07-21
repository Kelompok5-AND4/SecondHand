package com.igdev.secondhand.service

import com.igdev.secondhand.model.AllProductResponse
import com.igdev.secondhand.model.CategoryResponseItem
import com.igdev.secondhand.model.PagingProduct.BuyerProductResponse
import com.igdev.secondhand.model.PagingProduct.Product
import com.igdev.secondhand.model.UpdateResponse
import com.igdev.secondhand.model.addProduct.SellProductResponse
import com.igdev.secondhand.model.buyerorder.BuyerOrderResponse
import com.igdev.secondhand.model.detail.*
import com.igdev.secondhand.model.getAuth.ResponseAuth
import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.login.LoginResponse
import com.igdev.secondhand.model.notification.NotifResponseItem
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.model.register.RegisterResponse
import com.igdev.secondhand.model.sellerorder.PatchSellerOrderReq
import com.igdev.secondhand.model.sellerorder.PatchSellerOrderResponse
import com.igdev.secondhand.model.sellerorder.SellerOrderIdResponse
import com.igdev.secondhand.model.sellerorder.SellerOrderResponseItem
import com.igdev.secondhand.model.sellerproduct.PatchSellerProductIdResponse
import com.igdev.secondhand.model.sellerproduct.SellerProductDetail
import com.igdev.secondhand.model.sellerproduct.SellerProductResponseItem
import com.igdev.secondhand.model.settingaccount.SettingAccountRequest
import com.igdev.secondhand.model.settingaccount.SettingAccountResponse
import com.igdev.secondhand.model.wishlist.*
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

    @PUT("buyer/order/{id}")
    suspend fun putBuyerOrder(@Header("access_token") token: String,@Path("id")id: Int,@Body requestBody: PutOrderReq) : Response<PostOrderResponse>

    @DELETE("buyer/order/{id}")
    suspend fun deleteBuyerOrder(@Header("access_token")token: String,@Path("id")id:Int):Response<DeleteBuyerOrder>
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
        @Part file: MultipartBody.Part? = null,
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("base_price") base_price: RequestBody?,
        @Part("category_ids") categoryIds: List<Int>? = null,
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

    //paging
    @GET("buyer/product")
    suspend fun getProductAsBuyer(
        @Query("status") status: String? = null,
        @Query("category_id") categoryId: Int? = null,
        @Query("search") searchKeyword: String? = null,
        @Query("page") page: Int=1,
        @Query("per_page") itemsPerPage: Int=4
    ): Response<List<Product>>

    //wishlist
    @GET("buyer/wishlist")
    suspend fun getWishlist(
        @Header("access_token") token: String
    ) : List<GetWishlistResponse>

    @GET("buyer/wishlist/{id}")
    suspend fun getWishlistById(
        @Header("access_token") token: String,
        @Path ("id") id :Int
    ) : List<GetWishlistById>

    @POST("buyer/wishlist")
    suspend fun postWishlist(
        @Header("access_token") token: String,
        @Body requestBody : PostWishlistRequest
    ) : PostWishListResponse

    @DELETE("buyer/wishlist/{id}")
    suspend fun deleteWishlist(
        @Header("access_token") token: String,
        @Path ("id") id: Int
    ) : DeleteWishlistResponse

    @PATCH("seller/order/{id}")
    suspend fun patchSellerOrderId(@Header("access_token")token: String, @Path("id")id:Int, @Body request: PatchSellerOrderReq): Response<PatchSellerOrderResponse>

    @DELETE("seller/product/{id}")
    suspend fun deleteSellerProductId(@Header("access_token")token: String,@Path("id")id:Int): Response<Unit>

    @GET("seller/order/{id}")
    suspend fun getSellerOrderId(@Header("access_token")token: String,@Path("id")id:Int): Response<SellerOrderIdResponse>

    @PATCH("seller/product/{id}")
    suspend fun statusProduct(@Header("access_token")token:String,@Path("id")id:Int, @Body request: PatchSellerOrderReq): Response<PatchSellerProductIdResponse>

    //Seller Add Product
    @Multipart
    @PUT("seller/product/{id}")
    suspend fun editProduct(
        @Header("access_token") token: String,
        @Path("id")id: Int,
        @Part file: MultipartBody.Part? = null,
        @Part("name") name: RequestBody?,
        @Part("description") description: RequestBody?,
        @Part("base_price") base_price: RequestBody?,
        @Part("category_ids") categoryIds: List<Int>? = null,
        @Part("location") location: RequestBody?,
    ): SellProductResponse
}