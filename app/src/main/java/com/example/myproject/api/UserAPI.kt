package com.example.myproject.api

import com.example.myproject.database.UserClass
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface UserAPI {
    interface UserAPI {
        @GET("search/{id}")
        fun searchUser(@Path("id") id: Int): Call<UserClass>
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
