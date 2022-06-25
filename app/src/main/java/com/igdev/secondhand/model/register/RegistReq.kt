package com.igdev.secondhand.model.register


import com.google.gson.annotations.SerializedName

data class RegistReq(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("city")
    val city: String,
)