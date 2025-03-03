package com.example.myproject.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VerifyOtpResponse(
    @Expose
    @SerializedName("success") val success: Int,

    @Expose
    @SerializedName("message") val message: String
)