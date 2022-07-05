package com.igdev.secondhand.model.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Detail(
val productName : String,
val productPrice : String,
val sellerName : String,
val city : String,
val photo : String,
val deskripsi : String
) :Parcelable