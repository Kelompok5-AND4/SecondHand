package com.igdev.secondhand.model.detail


import com.google.gson.annotations.SerializedName

data class DeleteBuyerOrder(
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)