package com.igdev.secondhand.service

import com.igdev.secondhand.model.detail.PostOrderReq
import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.model.settingaccount.SettingAccountRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {
    suspend fun postRegUser(requestBody: RegistReq) = apiService.postRegUser(requestBody)
    suspend fun postLoginUser(requestBody: LoginReq) = apiService.postLogin(requestBody)
    suspend fun getAllProduct(status:String,categoryId:String) = apiService.getAllProduct(status,categoryId)
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
        file: MultipartBody.Part,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        categoryIds: List<Int>,
        location: RequestBody,
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
}