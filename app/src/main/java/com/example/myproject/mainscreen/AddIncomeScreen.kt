package com.example.myproject.mainscreen

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.myproject.R
import com.example.myproject.api.IncomeAPI
import com.example.myproject.database.IncomeResponse
import com.example.myproject.database.InsertIncomeClass
import com.example.myproject.loginandsignup.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun AddIncomeScreen(navController: NavHostController) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "income_grid") {
        composable("income_grid") { IncomeGrid(navController) }
        composable("income_details/{incomeName}/{incomeTypeId}") { backStackEntry ->
            val incomeName = backStackEntry.arguments?.getString("incomeName") ?: ""
            val incomeTypeId = backStackEntry.arguments?.getString("incomeTypeId")?.toIntOrNull() ?: 0
            IncomeDetailsScreen(navController, incomeName, incomeTypeId)
        }
    }
}

@Composable
fun IncomeGrid(navController: NavHostController) {
    val incomeTypeMap = mapOf(
        "เงินเดือน" to 1,
        "งานฟรีแลนซ์" to 2,
        "ขายของออนไลน์" to 3,
        "ลงทุนหุ้น" to 4,
        "กำไรจากคริปโต" to 5,
        "ดอกเบี้ยเงินฝาก" to 6
    )
    val incomeSources = listOf(
        "เงินเดือน" to R.drawable.work,
        "งานฟรีแลนซ์" to R.drawable.business,
        "ขายของออนไลน์" to R.drawable.shopping,
        "ลงทุนหุ้น" to R.drawable.trending,
        "กำไรจากคริปโต" to R.drawable.currency,
        "ดอกเบี้ยเงินฝาก" to R.drawable.savings
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "เพิ่มแหล่งรายได้",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(incomeSources) { (name, icon) ->
                IncomeCard(
                    text = name,
                    iconRes = icon,
                    onClick = {
                        val incomeTypeId = incomeTypeMap[name] ?: 0  // ✅ กำหนดค่าตามประเภท
                        navController.navigate("income_details/${name}/$incomeTypeId")
                    }
                )
            }
        }
    }
}

@Composable
fun IncomeCard(text: String, @DrawableRes iconRes: Int, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .size(150.dp) // Slightly reduced
            .clickable { onClick() }
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(56.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun IncomeDetailsScreen(navController: NavHostController, incomeName: String, incomeTypeId: Int) {
    var amount by remember { mutableStateOf("") }
    val myFont = FontFamily(Font(R.font.ibmplexsansthai_regular))

    val context = LocalContext.current
    val sharedPreferencesManager = remember { SharedPreferencesManager(context) }
    val selectedYear = sharedPreferencesManager.selectedYear // ✅ ใช้ SharedPreferencesManager
    val email = sharedPreferencesManager.userEmail // ✅ ดึง email จาก SharedPreference

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "รายละเอียดแหล่งรายได้",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "$incomeName",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ✅ ช่องกรอกจำนวนเงิน
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("กรอกจำนวนเงิน") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        val context = LocalContext.current
        Button(
            onClick = {
                if (amount.isNotEmpty() && email != null) {
                    val incomeAmount = amount.toDouble()
                    insertIncome(incomeAmount, incomeTypeId, email, selectedYear, navController, context)
                } else {
                    Toast.makeText(context, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .width(160.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "บันทึก",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium.copy(fontFamily = myFont),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ปุ่มกลับ
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .width(160.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text(
                text = "กลับ",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium.copy(fontFamily = myFont),
            )
        }
    }
}

// ✅ ฟังก์ชันส่งข้อมูลไปยัง API
private fun insertIncome(
    incomebalance: Double,
    incometype_id: Int,
    email: String,
    year: Int,
    navController: NavHostController,
    context: Context
) {
    val api = IncomeAPI.create()
    val request = InsertIncomeClass(incomebalance, incometype_id, email, year)

    api.insertIncome(request).enqueue(object : Callback<IncomeResponse> {
        override fun onResponse(call: Call<IncomeResponse>, response: Response<IncomeResponse>) {
            if (response.isSuccessful && response.body()?.success == 1) {
                Toast.makeText(context, "บันทึกข้อมูลสำเร็จ", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            } else {
                Toast.makeText(context, "เกิดข้อผิดพลาดในการบันทึกข้อมูล", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<IncomeResponse>, t: Throwable) {
            Toast.makeText(context, "การเชื่อมต่อล้มเหลว: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}