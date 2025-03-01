package com.example.myproject.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface DocumentAPI {
    @GET("user-files/1") // ต้องตรงกับ API ของ Backend
    suspend fun getDocuments(): List<DocumentItem>

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

data class DocumentItem(
    val id: Int,
    val document_url: String,
    val uploaded_at: String
)

