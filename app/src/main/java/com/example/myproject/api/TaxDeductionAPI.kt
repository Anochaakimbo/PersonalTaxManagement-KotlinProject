package com.example.myproject.api

import com.example.myproject.database.AllTaxDeductionClass
import com.example.myproject.database.InsertTaxDeductionClass
import com.example.myproject.database.TaxDeductionResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TaxDeductionAPI {
    @POST("/insertTaxDeduction")
    fun insertTaxDeduction(@Body taxDeduction: InsertTaxDeductionClass): Call<TaxDeductionResponse>

    @GET("/getTaxDeductionByUser/{user_id}")
    fun getIncomeTaxDeduction(@Path("user_id") userId: Int): Call<AllTaxDeductionClass>

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


