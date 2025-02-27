package com.example.myproject.api

import com.example.myproject.database.PasswordChangeRequest
import com.example.myproject.database.UserClass
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPI {
    interface UserAPI {
        @GET("search/{id}")
        fun searchUser(@Path("id") id: Int): Call<UserClass>

        @PUT("updateUser/{id}") // 🔥 เพิ่ม API สำหรับอัปเดตข้อมูล
        fun updateUser(@Path("id") id: Int, @Body user: UserClass): Call<Void>

        // ✅ เพิ่ม API สำหรับเปลี่ยนรหัสผ่าน
        @POST("change-password")
        fun changePassword(@Body request: PasswordChangeRequest): Call<Void>
    }

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


