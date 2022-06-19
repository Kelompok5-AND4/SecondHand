package com.igdev.secondhand.model.login


import com.google.gson.annotations.SerializedName

data class LoginReq(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)