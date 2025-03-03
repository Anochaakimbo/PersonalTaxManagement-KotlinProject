package com.example.myproject.api

import com.example.myproject.api.UserAPI.UserAPI
import com.example.myproject.database.AllincomeUserClass
import com.example.myproject.database.InsertIncomeClass
import com.example.myproject.database.IncomeResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IncomeAPI {
    @GET("/getAllIncomes")
    fun getAllIncome():Call<List<AllincomeUserClass>>

    @FormUrlEncoded
    @PUT("/updateIncome/{id}")
    fun updateIncome(
        @Path("id") id: Int,
        @Field("incomebalance") incomebalance : Int,
        @Field("user_id") user_id : Int,
        @Field("year") year : String,
        @Field("incometype_id") incometype_id : String): Call<AllincomeUserClass>

    @DELETE("/deleteIncome/{id}")
    fun deleteIncome(
        @Path("id") id: Int): Call<AllincomeUserClass>

    @POST("/insertIncome")
    fun insertIncome(@Body request: InsertIncomeClass): Call<IncomeResponse>

    companion object {
        fun create(): IncomeAPI {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(IncomeAPI::class.java)
        }
    }
}






