package com.igdev.secondhand.repository

import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.service.ApiHelper

class Repository(
    private val apiHelper: ApiHelper
) {
    // login and register
    suspend fun postRegUser(requestBody: RegistReq) = apiHelper.postRegUser(requestBody)
    suspend fun postLogin(requestBody: LoginReq) = apiHelper.postLoginUser(requestBody)
    suspend fun getAllProduct(status:String,categoryId:String) = apiHelper.getAllProduct(status,categoryId)
    suspend fun getAllCategory() = apiHelper.getAllCategory()
}