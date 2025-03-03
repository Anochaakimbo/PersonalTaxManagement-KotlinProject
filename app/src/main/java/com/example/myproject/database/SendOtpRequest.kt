package com.example.myproject.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SendOtpRequest(
    @Expose
    @SerializedName("email") val email: String
)
