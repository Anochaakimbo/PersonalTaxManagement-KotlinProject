package com.example.myproject.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class InsertTaxDeductionClass(
    @Expose
    @SerializedName("taxdeductiontype_balance") val balance: Double,

    @Expose
    @SerializedName("taxdeductiontype_id")val typeId: Int,

    @Expose
    @SerializedName("email")val email: String,

    @Expose
    @SerializedName("year")val year: Int
)
