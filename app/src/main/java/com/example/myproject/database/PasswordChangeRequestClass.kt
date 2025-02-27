package com.example.myproject.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PasswordChangeRequest(

    @Expose
    @SerializedName("userId") val userId: Int,

    @Expose
    @SerializedName("oldPassword") val oldPassword: String,

    @Expose
    @SerializedName("newPassword") val newPassword: String
)
