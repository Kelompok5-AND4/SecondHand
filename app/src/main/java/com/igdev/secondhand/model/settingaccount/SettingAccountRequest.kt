package com.igdev.secondhand.model.settingaccount

import com.google.gson.annotations.SerializedName

data class SettingAccountRequest(
    @SerializedName("current_password")
    val currentPassword: String,
    @SerializedName("new_password")
    val newPassword: String,
    @SerializedName("confirm_password")
    val confirmPassword: String
)
