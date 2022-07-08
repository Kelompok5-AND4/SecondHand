package com.igdev.secondhand.model.register


import com.google.gson.annotations.SerializedName

data class RegistReq(
    val email: String,
    @SerializedName("password")
    val password: String,
)