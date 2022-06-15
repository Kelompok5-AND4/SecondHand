package com.igdev.secondhand.service

import com.igdev.secondhand.model.RegisterReq
import javax.inject.Inject

class ApiHelper @Inject constructor(private val apiService: ApiService) {
    suspend fun postUser(requestBody:RegisterReq) = apiService.postUser(requestBody)
}