package com.igdev.secondhand.model.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserLogin(
    var username:String?,
    var email:String?,
    var password:String?,
    var phoneNumber:String?,
    var address:String?,
    var city:String?,
    var image:String?,
    var token:String?
) :Parcelable
