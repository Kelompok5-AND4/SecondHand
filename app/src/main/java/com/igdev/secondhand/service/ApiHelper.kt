package com.igdev.secondhand.service

import com.igdev.secondhand.model.detail.PostOrderReq
import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.model.sellerorder.PatchSellerOrderReq
import com.igdev.secondhand.model.settingaccount.SettingAccountRequest
import com.igdev.secondhand.model.wishlist.PostWishlistRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {
    suspend fun postRegUser(requestBody: RegistReq) = apiService.postRegUser(requestBody)
    suspend fun postLoginUser(requestBody: LoginReq) = apiService.postLogin(requestBody)
    suspend fun getAllProduct(status:String?=null,categoryId:String?=null,search :String?=null) = apiService.getAllProduct(status,categoryId,search)
    suspend fun getAllCategory() = apiService.getAllCategory()
    suspend fun getDataUser(token: String) = apiService.getDataUser(token)
    suspend fun updateUser(token: String,
                           image: MultipartBody.Part?,
                           name: RequestBody?,
                           phoneNumber: RequestBody?,
                           address: RequestBody?,
                           city: RequestBody?,) = apiService.updateDataUser(token, image, name, phoneNumber, address, city)
    //notification
    suspend fun getNotif(token:String) = apiService.GetNotif(token)

    suspend fun postProduct(
        token: String,
        file: MultipartBody.Part?,
        name: RequestBody?,
        description: RequestBody?,
        base_price: RequestBody?,
        categoryIds: List<Int>?,
        location: RequestBody?,
    ) = apiService.postProduct(token, file, name, description, base_price, categoryIds, location)
    //details
    suspend fun getDetail(id:Int) = apiService.getIdProduct(id)
    suspend fun getSellerProductDetail(token: String,id:Int) = apiService.getSellerProductDetail(token, id)
    //buyer history
    suspend fun getBuyerHistory(token:String) = apiService.getBuyerHistory(token)
    //seller product
    suspend fun getSellerProduct(token:String) = apiService.getSellerProduct(token)
    //seller order
    suspend fun getSellerOrder(token:String) = apiService.getSellerOrder(token)
    //post order
    suspend fun postOrder(token: String,requestBody: PostOrderReq)= apiService.postBuyerOrder(token,requestBody)
    //setting account
    suspend fun settingAccount(token: String, requestBody : SettingAccountRequest ) = apiService.updatePassword(token, requestBody)
    //wishlist
    suspend fun getWishlist(token: String) = apiService.getWishlist(token)
    suspend fun getWishlistId(token: String, id: Int) = apiService.getWishlistById(token,id)
    suspend fun postWishlist(token: String, request: PostWishlistRequest) = apiService.postWishlist(token,request)
    suspend fun deleteWishlist(token: String, id: Int) = apiService.deleteWishlist(token, id)
    suspend fun getProductAsBuyer(
        status: String? = null,
        categoryId: Int? = null,
        searchKeyword: String? = null,
        page: Int=1,
        itemsPerPage: Int=20
    ) = apiService.getProductAsBuyer(
        status,
        categoryId,
        searchKeyword,
        page,
        itemsPerPage
    )
    //seller order patch and delete
    suspend fun patchSellerOrderId(token: String,id:Int, request: PatchSellerOrderReq) = apiService.patchSellerOrderId(token,id, request)
    suspend fun patchSellerProductId(token: String,id:Int, request: PatchSellerOrderReq) = apiService.statusProduct(token,id, request)

    suspend fun deleteSellerProduct(token: String,id:Int) = apiService.deleteSellerProductId(token,id)
    suspend fun getSellerOrderId(token: String,id:Int) = apiService.getSellerOrderId(token,id)

}