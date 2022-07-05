package com.igdev.secondhand.model.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductPreview(
    var productName:String?,
    var productPrice:String?,
    var productDescriptionn:String?,
    var productCategory: String?,
    var productImage:String?
):Parcelable
