package com.igdev.secondhand.service

import com.igdev.secondhand.model.RegistReq
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {
    suspend fun postUser(requestBody:RegistReq) = apiService.postUser(requestBody)
}