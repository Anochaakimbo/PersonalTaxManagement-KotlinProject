package com.example.myproject.api

import com.example.myproject.database.Document
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface DocumentAPI {

    // 📌 ดึงเอกสารทั้งหมดของผู้ใช้
    @GET("user-files/{user_id}")
    fun getUserFiles(
        @Path("user_id") userId: String
    ): Call<List<Document>>

    // 📌 อัปโหลดเอกสาร
    @Multipart
    @POST("upload-document/{user_id}")
    fun uploadDocument(
        @Path("user_id") userId: String,
        @Part file: MultipartBody.Part
    ): Call<Document>

    // 📌 อัปโหลดรูปภาพ
    @Multipart
    @POST("upload-image/{user_id}")
    fun uploadImage(
        @Path("user_id") userId: String,
        @Part file: MultipartBody.Part
    ): Call<Document>

    // 📌 ลบเอกสารหรือรูปภาพ
    @DELETE("delete-file/{file_id}")
    fun deleteFile(
        @Path("file_id") fileId: String
    ): Call<Document>

    companion object {
        fun create(): DocumentAPI {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DocumentAPI::class.java)
        }
    }
}