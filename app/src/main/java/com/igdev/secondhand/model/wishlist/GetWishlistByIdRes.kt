package com.igdev.secondhand.model.wishlist


import com.google.gson.annotations.SerializedName

data class GetWishlistById(
    @SerializedName("id")
    val id: Int,
    @SerializedName("Product")
    val product: ProductId,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("user_id")
    val userId: Int
)

data class ProductId(
    @SerializedName("base_price")
    val basePrice: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image_name")
    val imageName: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)