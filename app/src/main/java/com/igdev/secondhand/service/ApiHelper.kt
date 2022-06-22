package com.igdev.secondhand.service

import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.register.RegistReq
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {
    suspend fun postRegUser(requestBody: RegistReq) = apiService.postRegUser(requestBody)
    suspend fun postLoginUser(requestBody: LoginReq) = apiService.postLogin(requestBody)

    //notification
    suspend fun getNotif(token:String) = apiService.GetNotif(token)

}