package com.igdev.secondhand.model


import com.google.gson.annotations.SerializedName

data class CategoryResponseItem(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("updatedAt")
    val updatedAt: String
)