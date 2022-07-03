package com.igdev.secondhand.service

import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.register.RegistReq
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

}