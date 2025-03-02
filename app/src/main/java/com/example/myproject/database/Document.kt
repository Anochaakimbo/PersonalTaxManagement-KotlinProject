package com.example.myproject.database

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Document(
    @Expose
    @SerializedName("id") val id: Int,

    @Expose
    @SerializedName("user_id") val userId: Int,

    @Expose
    @SerializedName("document_url") val documentUrl: String,

    @Expose
    @SerializedName("file_type") val fileType: String, // "document" หรือ "image"

    @Expose
    @SerializedName("uploaded_at") val uploadedAt: String,

    @Expose
    @SerializedName("year")val year: Int
) : Parcelable



