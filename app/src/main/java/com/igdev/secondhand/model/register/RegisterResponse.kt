package com.igdev.secondhand.model.register


import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: Any,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("city")
    val city: String
)