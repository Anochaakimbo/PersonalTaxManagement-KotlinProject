package com.example.myproject.mainscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myproject.api.IncomeAPI
import com.example.myproject.api.TaxDeductionAPI
import com.example.myproject.database.AllTaxDeductionClass
import com.example.myproject.database.AllincomeUserClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import taxDeductionTypeMap

@Composable
fun EditTaxdeducScreen(navController: NavHostController) {
    val taxData = navController.previousBackStackEntry?.savedStateHandle?.get<Map<String, Any>>("taxData")
    Log.d("EditTaxdeducScreen", "Received taxData: $taxData")

    // ดึงข้อมูลจาก Map
    if (taxData != null) {
        val taxId = taxData["id"] as Int
        val taxdeductiontype_id = taxData["taxdeductiontype_id"] as String
        val taxdeductiontype_name = taxData["taxdeductiontype_name"] as String
        val year = taxData["year"] as String
        val taxdeductionbalance = when (val value = taxData["taxdeductionbalance"]) {
            is Int -> value.toDouble()
            is Double -> value
            else -> 0.0
        }
        val userId = taxData["user_id"] as Int


        EditTaxForm(
            taxId = taxId,
            taxdeductiontype_id = taxdeductiontype_id,
            taxdeductiontype_name = taxdeductiontype_name,
            year = year,
            taxdeductionbalance = taxdeductionbalance,
            userId = userId,
            onUpdateSuccess = {

                navController.popBackStack()
            }
        )
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("ไม่พบข้อมูลลดหย่อนภาษีที่ต้องการแก้ไข")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("กลับ")
            }
        }
    }
}

@Composable
fun EditTaxForm(
    taxId: Int,
    taxdeductiontype_id: String,
    taxdeductiontype_name: String,
    year: String,
    taxdeductionbalance: Double,
    userId: Int,
    onUpdateSuccess: () -> Unit
) {
    val context = LocalContext.current
    var selectedTaxTypeId by remember { mutableStateOf(taxdeductiontype_id) }
    var selectedTaxTypeName by remember { mutableStateOf(taxdeductiontype_name) }
    var taxBalanceText by remember { mutableStateOf(taxdeductionbalance.toInt().toString()) }
    var expanded by remember { mutableStateOf(false) }

    // รายการประเภทลดหย่อนภาษีจาก taxDeductionTypeMap
    val taxTypes = taxDeductionTypeMap.map { entry ->
        mapOf("id" to entry.key, "name" to entry.value)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )

    {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "แก้ไขลดหย่อนภาษี",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ส่วนเลือกประเภทลดหย่อนภาษี
        Box {
            OutlinedTextField(
                value = selectedTaxTypeName,
                onValueChange = { },
                label = { Text("ประเภทลดหย่อนภาษี") },
                readOnly = true,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown",
                        modifier = Modifier.clickable { expanded = true }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                taxTypes.forEach { taxType ->
                    DropdownMenuItem(
                        text = { Text(taxType["name"] as String) },
                        onClick = {
                            selectedTaxTypeId = taxType["id"] as String
                            selectedTaxTypeName = taxType["name"] as String
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ช่องกรอกจำนวนเงิน
        OutlinedTextField(
            value = taxBalanceText,
            onValueChange = { newValue ->
                // รับเฉพาะตัวเลขจำนวนเต็มเท่านั้น
                if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                    taxBalanceText = newValue
                }
            },
            label = { Text("จำนวนเงิน (บาท)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ปุ่มอัปเดต
        Button(
            onClick = {
                // ตรวจสอบข้อมูล
                if (taxBalanceText.isBlank()) {
                    Toast.makeText(context, "กรุณาระบุจำนวนเงิน", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val taxBalanceValue = try {
                    taxBalanceText.toInt()
                } catch (e: Exception) {
                    Toast.makeText(context, "จำนวนเงินไม่ถูกต้อง", Toast.LENGTH_SHORT).show()
                    return@Button
                }


                try {
                    val taxDeductionApi = TaxDeductionAPI.create()

                    taxDeductionApi.updateIncome(
                        id = taxId,
                        taxdeductiontype_balance = taxBalanceValue,
                        user_id = userId,
                        year = year,
                        taxdeductiontype_id = selectedTaxTypeId
                    ).enqueue(object : Callback<AllTaxDeductionClass> {
                        override fun onResponse(
                            call: Call<AllTaxDeductionClass>,
                            response: Response<AllTaxDeductionClass>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "อัปเดตข้อมูลลดหย่อนภาษีสำเร็จ", Toast.LENGTH_SHORT).show()
                                // เรียกใช้ callback เมื่ออัปเดตสำเร็จ
                                onUpdateSuccess()
                            } else {
                                Toast.makeText(context, "ไม่สามารถอัปเดตข้อมูลลดหย่อนภาษีได้: รหัสข้อผิดพลาด ${response.code()}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<AllTaxDeductionClass>, t: Throwable) {
                            Toast.makeText(context, "เกิดข้อผิดพลาด: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
                } catch (e: Exception) {
                    Toast.makeText(context, "เกิดข้อผิดพลาด: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("บันทึกการแก้ไข")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ปุ่มยกเลิก
        OutlinedButton(
            onClick = { onUpdateSuccess() },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("ยกเลิก")
        }


    }
}