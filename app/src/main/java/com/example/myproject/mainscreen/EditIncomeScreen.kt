package com.example.myproject.mainscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myproject.api.IncomeAPI
import com.example.myproject.database.AllincomeUserClass
import incomeTypeMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EditIncomeScreen(navController: NavHostController) {
    // ดึงข้อมูลจากหน้า HomeScreen ที่ถูกส่งมา
    val incomeData = navController.previousBackStackEntry?.savedStateHandle?.get<Map<String, Any>>("incomeData")
    Log.d("EditIncomeScreen", "Received incomeData: $incomeData")
    // ดึงข้อมูลจาก Map
    if (incomeData != null) {
        val incomeId = incomeData["id"] as Int
        val incomeTypeId = incomeData["incometype_id"] as String
        val incomeTypeName = incomeData["incometype_name"] as String
        val year = incomeData["year"] as String
        val incomeBalance = when (val value = incomeData["incomebalance"]) {
            is Int -> value.toDouble()
            is Double -> value
            else -> 0.0
        }
        val userId = incomeData["user_id"] as Int

        // สร้างฟอร์มแก้ไขด้วยค่าเริ่มต้นจากข้อมูลที่ได้รับ
        EditIncomeForm(
            incomeId = incomeId,
            incomeTypeId = incomeTypeId,
            incomeTypeName = incomeTypeName,
            year = year,
            incomeBalance = incomeBalance,
            userId = userId,
            onUpdateSuccess = {
                // เมื่อแก้ไขสำเร็จ ให้กลับไปหน้าเดิม
                navController.popBackStack()
            }
        )
    } else {
        // กรณีไม่มีข้อมูล ให้แสดงข้อความแจ้งเตือนและปุ่มกลับ
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("ไม่พบข้อมูลรายได้ที่ต้องการแก้ไข")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.popBackStack() }) {
                Text("กลับ")
            }
        }
    }
}

@Composable
fun EditIncomeForm(
    incomeId: Int,
    incomeTypeId: String,
    incomeTypeName: String,
    year: String,
    incomeBalance: Double,
    userId: Int,
    onUpdateSuccess: () -> Unit
) {
    val context = LocalContext.current
    var selectedIncomeTypeId by remember { mutableStateOf(incomeTypeId) }
    var selectedIncomeTypeName by remember { mutableStateOf(incomeTypeName) }
    var incomeBalanceText by remember { mutableStateOf(incomeBalance.toInt().toString()) }
    var expanded by remember { mutableStateOf(false) }

    // รายการประเภทรายได้จาก incomeTypeMap
    val incomeTypes = incomeTypeMap.map { entry ->
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
            text = "แก้ไขรายได้",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ส่วนเลือกประเภทรายได้
        Box {
            OutlinedTextField(
                value = selectedIncomeTypeName,
                onValueChange = { },
                label = { Text("ประเภทรายได้") },
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
                incomeTypes.forEach { incomeType ->
                    DropdownMenuItem(
                        text = { Text(incomeType["name"] as String) },
                        onClick = {
                            selectedIncomeTypeId = incomeType["id"] as String
                            selectedIncomeTypeName = incomeType["name"] as String
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ช่องกรอกจำนวนเงิน
        OutlinedTextField(
            value = incomeBalanceText,
            onValueChange = { newValue ->
                // รับเฉพาะตัวเลขจำนวนเต็มเท่านั้น
                if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                    incomeBalanceText = newValue
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
                if (incomeBalanceText.isBlank()) {
                    Toast.makeText(context, "กรุณาระบุจำนวนเงิน", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val incomeBalanceValue = try {
                    incomeBalanceText.toInt()
                } catch (e: Exception) {
                    Toast.makeText(context, "จำนวนเงินไม่ถูกต้อง", Toast.LENGTH_SHORT).show()
                    return@Button
                }


                try {
                    val incomeApi = IncomeAPI.create()
                    val updatedIncome = AllincomeUserClass(
                        id = incomeId,
                        user_id = userId,
                        incometype_id = selectedIncomeTypeId,
                        year = year,
                        incomebalance = incomeBalanceValue
                    )

                    incomeApi.updateIncome(
                        id = incomeId,
                        incomebalance = incomeBalanceValue,
                        user_id = userId,
                        year = year,
                        incometype_id = selectedIncomeTypeId
                    ).enqueue(object : Callback<AllincomeUserClass> {
                        override fun onResponse(
                            call: Call<AllincomeUserClass>,
                            response: Response<AllincomeUserClass>
                        ) {
                            if (response.isSuccessful) {
                                Toast.makeText(context, "อัปเดตข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show()
                                // เรียกใช้ callback เมื่ออัปเดตสำเร็จ
                                onUpdateSuccess()
                            } else {
                                Toast.makeText(context, "ไม่สามารถอัปเดตข้อมูลได้: รหัสข้อผิดพลาด ${response.code()}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<AllincomeUserClass>, t: Throwable) {
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