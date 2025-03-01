package com.example.myproject.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TaxDeductionResponse(
    @Expose
    @SerializedName("success") val success:Int,

    @Expose
    @SerializedName("id") val id:Int,

    @Expose
    @SerializedName("message") val message:String,

    )
