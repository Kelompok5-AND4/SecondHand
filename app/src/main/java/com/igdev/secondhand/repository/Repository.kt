package com.igdev.secondhand.repository

import com.igdev.secondhand.datastore.DataStore
import com.igdev.secondhand.model.User
import com.igdev.secondhand.model.detail.PostOrderReq
import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.model.settingaccount.SettingAccountRequest
import com.igdev.secondhand.service.ApiHelper
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository(
    private val apiHelper: ApiHelper,private val dataStore: DataStore
) {
    // login and register
    suspend fun postRegUser(requestBody: RegistReq) = apiHelper.postRegUser(requestBody)
    suspend fun postLogin(requestBody: LoginReq) = apiHelper.postLoginUser(requestBody)
    suspend fun getAllProduct(status:String,categoryId:String) = apiHelper.getAllProduct(status,categoryId)
    suspend fun getAllCategory() = apiHelper.getAllCategory()
    suspend fun setDatastore(user: User) = dataStore.setToken(user)
    suspend fun getDatastore() : Flow<User> = dataStore.getToken()
    suspend fun deleteToken() = dataStore.delete()
    suspend fun getDataUser(token: String) = apiHelper.getDataUser(token)
    suspend fun updateDataUser(
        token : String,
        image: MultipartBody.Part?,
        name: RequestBody?,
        phoneNumber: RequestBody?,
        address: RequestBody?,
        city: RequestBody?,
    ) = apiHelper.updateUser(token, image, name, phoneNumber, address, city)
    //notification
    suspend fun getNotif(token :String) = apiHelper.getNotif(token)
    //details
    suspend fun getDetail(id:Int) = apiHelper.getDetail(id)
    suspend fun getSellerDetailProduct(token: String,id:Int) = apiHelper.getSellerProductDetail(token,id)

    suspend fun postProduct(
        token: String,
        file : MultipartBody.Part,
        name: RequestBody,
        description: RequestBody,
        base_price: RequestBody,
        categoryIds: List<Int>,
        location: RequestBody,
    ) = apiHelper.postProduct(token, file, name, description, base_price, categoryIds, location)
    //buyer history
    suspend fun getBuyerHistory(token :String) = apiHelper.getBuyerHistory(token)
    //seller product
    suspend fun getSellerProduct(token :String) = apiHelper.getSellerProduct(token)
    //seller order
    suspend fun getSellerOrder(token :String) = apiHelper.getSellerOrder(token)
    //post Bid
    suspend fun postOrder(token: String,requestBody: PostOrderReq)= apiHelper.postOrder(token, requestBody)
    //setting account
    suspend fun settingAccount(token: String, requestBody: SettingAccountRequest) = apiHelper.settingAccount(token,requestBody)
}