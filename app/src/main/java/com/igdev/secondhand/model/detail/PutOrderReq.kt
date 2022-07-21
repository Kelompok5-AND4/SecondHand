package com.igdev.secondhand.model.detail

import com.google.gson.annotations.SerializedName

data class PutOrderReq(
    @SerializedName("bid_price")
    val bid_price: String
)
