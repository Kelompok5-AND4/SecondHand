package com.igdev.secondhand.model.settingaccount


import com.google.gson.annotations.SerializedName

data class SettingAccountResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("name")
    val name: String
)