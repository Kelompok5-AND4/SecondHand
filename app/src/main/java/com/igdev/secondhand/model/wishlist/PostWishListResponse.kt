package com.igdev.secondhand.model.wishlist


import com.google.gson.annotations.SerializedName

data class PostWishListResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("Product")
    val product: Product
)