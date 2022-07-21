package com.igdev.secondhand.model.register


import com.google.gson.annotations.SerializedName

data class RegistReq(
    @SerializedName("full_name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)