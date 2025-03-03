package com.example.myproject.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class VerifyOtpRequest(
    @Expose
    @SerializedName("email") val email: String,

    @Expose
    @SerializedName("otp") val otp: String
)
