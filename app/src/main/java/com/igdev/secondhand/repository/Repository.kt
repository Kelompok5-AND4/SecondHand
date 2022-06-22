package com.igdev.secondhand.repository

import com.igdev.secondhand.datastore.DataStore
import com.igdev.secondhand.model.User
import com.igdev.secondhand.model.login.LoginReq
import com.igdev.secondhand.model.register.RegistReq
import com.igdev.secondhand.service.ApiHelper
import kotlinx.coroutines.flow.Flow

class Repository(
    private val apiHelper: ApiHelper,
    private val dataStore: DataStore
) {
    // login and register
    suspend fun postRegUser(requestBody: RegistReq) = apiHelper.postRegUser(requestBody)
    suspend fun postLogin(requestBody: LoginReq) = apiHelper.postLoginUser(requestBody)
    suspend fun setDatastore(user: User) = dataStore.setToken(user)
    suspend fun getDatastore() :Flow<User> = dataStore.getToken()
    suspend fun delete() = dataStore.delete()

    //notification
    suspend fun getNotif(token :String) = apiHelper.getNotif(token)
}