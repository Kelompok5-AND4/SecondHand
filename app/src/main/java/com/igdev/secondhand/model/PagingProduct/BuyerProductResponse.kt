package com.igdev.secondhand.model.PagingProduct


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class BuyerProductResponse(
    @SerializedName("data")
    val `data`: List<Product>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("per_page")
    val perPage: Int,
    val nextPage:Int? = null
)

