package com.example.myproject.database

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AllincomeUserClass(
    @Expose
    @SerializedName("id") val id:Int,

    @Expose
    @SerializedName("incomebalance")val incomebalance: Int,

    @Expose
    @SerializedName("user_id")val user_id:Int,

    @Expose
    @SerializedName("year")val year: String,

    @Expose
    @SerializedName("incometype_id")val incometype_id:String): Parcelable {}
