package com.example.myproject.api

import com.example.myproject.database.AllTaxDeductionClass
import com.example.myproject.database.AllincomeUserClass
import com.example.myproject.database.InsertTaxDeductionClass
import com.example.myproject.database.TaxDeductionResponse
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

interface TaxDeductionAPI {
    @POST("/insertTaxDeduction")
    fun insertTaxDeduction(@Body taxDeduction: InsertTaxDeductionClass): Call<TaxDeductionResponse>

    @FormUrlEncoded
    @PUT("/updateTaxDeduction/{id}")
    fun updateIncome(
        @Path("id") id: Int,
        @Field("taxdeductiontype_balance") taxdeductiontype_balance : Int,
        @Field("user_id") user_id : Int,
        @Field("year") year : String,
        @Field("taxdeductiontype_id") taxdeductiontype_id : String): Call<AllTaxDeductionClass>

    @DELETE("/deleteTaxDeduction/{id}")
    fun deleteTax(
        @Path("id") id: Int): Call<AllTaxDeductionClass>

    @GET("/getAllTaxDeductions")
    fun getAllTaxDeduction():Call<List<AllTaxDeductionClass>>

    companion object {
        fun create(): TaxDeductionAPI {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(TaxDeductionAPI::class.java)
        }
    }
}


