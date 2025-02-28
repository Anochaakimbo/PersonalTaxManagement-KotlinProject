package com.example.myproject.api

import com.example.myproject.database.PasswordChangeRequest
import com.example.myproject.database.UserClass
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface UserAPI {
    interface UserAPI {
        @GET("search/{id}")
        fun searchUser(@Path("id") id: Int): Call<UserClass>

        @PUT("updateUser/{id}") // 🔥 เพิ่ม API สำหรับอัปเดตข้อมูล
        fun updateUser(@Path("id") id: Int, @Body user: UserClass): Call<Void>

        @POST("change-password")
        fun changePassword(@Body request: PasswordChangeRequest): Call<Void>

    }

    @Multipart
    @POST("uploadProfilePicture")
    fun uploadProfilePicture(
        @Part("userId") userId: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<UserClass>

    companion object {
        fun create(): UserAPI {
            val userClient : UserAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserAPI ::class.java)
            return userClient
        }
    }

}


