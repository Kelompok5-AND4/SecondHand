package com.igdev.secondhand.model.wishlist

import com.google.gson.annotations.SerializedName

data class PostWishlistRequest(
    @SerializedName("product_id")
    val product_id : Int
)
