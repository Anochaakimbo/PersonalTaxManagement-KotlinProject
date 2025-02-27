package com.example.myproject.database

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserClass(

    @Expose
    @SerializedName("id") val id : Int,

    @Expose
    @SerializedName("email") val email : String,

    @Expose
    @SerializedName("fname") val fname : String,

    @Expose
    @SerializedName("lname") val lname : String,

    @Expose
    @SerializedName("gender") val gender : String
)
