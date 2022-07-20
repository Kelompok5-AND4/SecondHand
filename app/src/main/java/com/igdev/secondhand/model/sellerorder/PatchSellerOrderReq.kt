package com.igdev.secondhand.model.sellerorder

import com.google.gson.annotations.SerializedName

data class PatchSellerOrderReq(
    @SerializedName("status")
    val status: String
)