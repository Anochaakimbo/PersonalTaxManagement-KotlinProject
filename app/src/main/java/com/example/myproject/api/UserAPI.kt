package com.example.myproject.api


import com.example.myproject.database.PasswordChangeRequest
import com.example.myproject.database.ResetPasswordRequest
import com.example.myproject.database.SendOtpRequest
import com.example.myproject.database.UploadResponse
import com.example.myproject.database.UserClass
import com.example.myproject.database.UserProfileResponse
import com.example.myproject.database.VerifyOtpRequest
import com.example.myproject.database.VerifyOtpResponse
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

        @Multipart
        @POST("uploadProfileImage/{id}")
        fun uploadProfileImage(
            @Path("id") userId: Int,
            @Part profile_image: MultipartBody.Part
        ): Call<UploadResponse>

        @GET("getUserProfile/{id}")
        fun getUserProfile(@Path("id") userId: Int): Call<UserProfileResponse>

        @POST("send-otp")
        fun sendOtp(@Body request: SendOtpRequest): Call<SendOtpRequest>

        @POST("verify-otp")
        fun verifyOtp(@Body request: VerifyOtpRequest): Call<VerifyOtpResponse>

        @POST("reset-password")
        fun resetPassword(@Body request: ResetPasswordRequest): Call<ResetPasswordRequest>


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


