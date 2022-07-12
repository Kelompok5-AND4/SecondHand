package com.igdev.secondhand.model.detail

import com.google.gson.annotations.SerializedName

data class PostOrderReq(

    @SerializedName("product_id")
    val product_id: String,
    @SerializedName("bid_price")
    val bid_price: String
)
