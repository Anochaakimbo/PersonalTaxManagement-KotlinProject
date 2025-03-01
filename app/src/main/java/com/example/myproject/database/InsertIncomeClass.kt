package com.example.myproject.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class InsertIncomeClass(
    @Expose
    @SerializedName("incomebalance") val incomebalance: Double,

    @Expose
    @SerializedName("incometype_id")val incometype_id: Int,

    @Expose
    @SerializedName("email")val email: String,

    @Expose
    @SerializedName("year")val year: Int
)
