package com.example.myproject.api


import com.example.myproject.database.UserClass
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface RegisterAPI {
    @FormUrlEncoded
    @POST("insertUser")
    fun insertUser(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("fname") fname: String,
        @Field("lname") lname: String,
        @Field("gender") gender: String,
    ): Call<UserClass>
    companion object {
        fun create(): RegisterAPI {
            val registerClient = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RegisterAPI::class.java)
            return registerClient
        }
    }
}