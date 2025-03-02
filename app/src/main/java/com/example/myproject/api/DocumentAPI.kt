package com.example.myproject.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface DocumentAPI {
    // ✅ ดึงเอกสารของ user_id = 1 (จากโค้ดเดิม)
    @GET("user-files/1")
    suspend fun getDocuments(): List<DocumentItem>

    // ✅ ดึงเอกสารของ user_id ที่กำหนด (แก้ไขให้รองรับการกรองปี)
    @GET("user-files/{user_id}")
    suspend fun getDocuments(
        @Path("user_id") userId: Int,
        @Query("year") year: String? = null // รองรับการกรองตามปี
    ): List<DocumentItem>

    @Multipart
    @POST("upload-image/{user_id}")
    suspend fun uploadDocument(
        @Path("user_id") userId: Int,
        @Part file: MultipartBody.Part,
        @Part("year") year: RequestBody // เพิ่มปีใน Part
    )

    @GET("user-files/document/{document_id}")
    suspend fun getDocumentById(@Path("document_id") documentId: Int): DocumentItem

    // ดึงข้อมูลเอกสารตาม user_id และ year
    @GET("user-files/{user_id}/year/{year}")
    suspend fun getDocumentsByUserAndYear(
        @Path("user_id") userId: Int, // รับ user_id จาก URL
        @Path("year") year: Int // รับ year จาก URL
    ): List<DocumentItem>

    // ✅ ลบไฟล์จากเซิร์ฟเวอร์
    @DELETE("delete-file/{id}")
    suspend fun deleteFile(@Path("id") fileId: Int)

    companion object {
        fun create(): DocumentAPI {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/") // ใช้ IP จำลอง Emulator
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DocumentAPI::class.java)
        }
    }
}

// ✅ โครงสร้างข้อมูลเอกสาร
data class DocumentItem(
    val id: Int,
    val document_url: String,
    val uploaded_at: String,
    val year: Int? // เพิ่มฟิลด์ year
)
