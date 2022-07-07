package com.igdev.secondhand.model.sellerorder


import com.google.gson.annotations.SerializedName

data class SellerOrderResponseItem(
    @SerializedName("base_price")
    val basePrice: String,
    @SerializedName("buyer_id")
    val buyerId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_product")
    val imageProduct: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("Product")
    val product: Product,
    @SerializedName("product_id")
    val productId: Int,
    @SerializedName("product_name")
    val productName: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("transaction_date")
    val transactionDate: Any,
    @SerializedName("updatedAt")
    val updatedAt: String,
    @SerializedName("User")
    val user: UserX
)

data class Product(
    @SerializedName("base_price")
    val basePrice: Int,
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
    @SerializedName("status")
    val status: String,
    @SerializedName("User")
    val user: UserX,
    @SerializedName("user_id")
    val userId: Int
)

data class UserX(
    @SerializedName("address")
    val address: String,
    @SerializedName("city")
    val city: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("phone_number")
    val phoneNumber: String
)