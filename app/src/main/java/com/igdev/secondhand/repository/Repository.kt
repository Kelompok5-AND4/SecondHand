package com.igdev.secondhand.repository

import com.igdev.secondhand.model.RegistReq
import com.igdev.secondhand.service.ApiHelper

class Repository(
    private val apiHelper: ApiHelper
) {
    // login and register
    suspend fun postUser(requestBody: RegistReq) = apiHelper.postUser(requestBody)

}