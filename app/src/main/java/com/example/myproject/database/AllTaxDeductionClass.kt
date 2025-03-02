package com.example.myproject.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AllTaxDeductionClass(
    @Expose
    @SerializedName("id") val id:Int,

    @Expose
    @SerializedName("taxdeductiontype_balance")val taxdeductiontype_balance: Int,

    @Expose
    @SerializedName("user_id")val user_id:Int,

    @Expose
    @SerializedName("year")val year: String,

    @Expose
    @SerializedName("taxdeductiontype_name")val taxdeductiontype_name:String,
)
