package com.igdev.secondhand.model.wishlist


import com.google.gson.annotations.SerializedName

data class DeleteWishlistResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)