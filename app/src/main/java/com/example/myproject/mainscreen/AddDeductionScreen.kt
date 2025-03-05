package com.example.myproject.mainscreen

import android.content.Context
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myproject.R
import com.example.myproject.api.TaxDeductionAPI
import com.example.myproject.database.InsertTaxDeductionClass
import com.example.myproject.database.TaxDeductionResponse
import com.example.myproject.loginandsignup.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun AddDeductionScreen(navController: NavHostController) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "deduction_grid") {
        composable("deduction_grid") { DeductionGrid(navController) }
        composable("deduction_details/{deductionName}/{deductionTypeId}") { backStackEntry ->
            val deductionName = backStackEntry.arguments?.getString("deductionName") ?: ""
            val deductionTypeId = backStackEntry.arguments?.getString("deductionTypeId")?.toIntOrNull() ?: 0
            DeductionDetailsScreen(navController, deductionName, deductionTypeId)
        }
    }
}

@Composable
fun DeductionGrid(navController: NavHostController) {
    val deductionTypeMap = mapOf(
        "ประกันสังคม" to 1,
        "Easy E-Receipt" to 2,
        "ดอกเบี้ยที่บ้าน" to 3,
        "ประกันชีวิตทั่วไป" to 4,
        "บริจาคทั่วไป" to 5,
        "Thai ESG" to 6
    )
    val deductionItems = listOf(
        "ประกันสังคม" to R.drawable.account,
        "Easy E-Receipt" to R.drawable.receipt,
        "ดอกเบี้ยที่บ้าน" to R.drawable.home_24,
        "ประกันชีวิตทั่วไป" to R.drawable.shield,
        "บริจาคทั่วไป" to R.drawable.stars,
        "Thai ESG" to R.drawable.compost
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "เพิ่มค่าลดหย่อน",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            deductionItems.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowItems.forEach { (name, icon) ->
                        DeductionCard(
                            text = name,
                            iconRes = icon,
                            onClick = {
                                val deductionTypeId = deductionTypeMap[name] ?: 0
                                navController.navigate("deduction_details/${name}/$deductionTypeId")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeductionCard(text: String, @DrawableRes iconRes: Int, onClick: () -> Unit) {
    OutlinedCard(
        modifier = Modifier
            .size(150.dp)
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
fun DeductionDetailsScreen(navController: NavHostController, deductionName: String, deductionTypeId: Int) {
    var amount by remember { mutableStateOf("") }
    val myFont = FontFamily(Font(R.font.ibmplexsansthai_regular))
    val context = LocalContext.current
    val sharedPreferencesManager = remember { SharedPreferencesManager(context) }
    val selectedYear = sharedPreferencesManager.selectedYear
    val email = sharedPreferencesManager.userEmail

    // Define deduction limits (for display purposes only)
    val deductionLimits = mapOf(
        "ประกันสังคม" to 9000.0,
        "Easy E-Receipt" to 50000.0,
        "ดอกเบี้ยที่บ้าน" to 100000.0,
        "ประกันชีวิตทั่วไป" to 100000.0,
        "Thai ESG" to 210000.0
    )

    // แปลงค่า amount เป็นตัวเลข ถ้าไม่สามารถแปลงได้ให้เป็น 0
    val enteredAmount = amount.toDoubleOrNull() ?: 0.0
    val limit = deductionLimits[deductionName] ?: 0.0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "รายละเอียดค่าลดหย่อน",
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        val formattedEnteredAmount = String.format("%,d", enteredAmount.toInt())
        val formattedLimit = String.format("%,d", limit.toInt())

        Text(
            text = "$deductionName",
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "ซื้อไป: $formattedEnteredAmount บาท",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Text(
            text = "ใช้สิทธิ์ลดหย่อนได้สูงสุด: $formattedLimit บาท",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = {
                // Limit input to numbers only
                amount = it.filter { char -> char.isDigit() }
            },
            label = { Text("จำนวนเงินค่าลดหย่อน") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (amount.isNotEmpty() && email != null) {
                    insertTaxDeduction(enteredAmount, deductionTypeId, email, selectedYear, navController, context)
                } else {
                    Toast.makeText(context, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .width(160.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
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

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .width(160.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
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



private fun insertTaxDeduction(
    taxDeductionAmount: Double,
    deductionTypeId: Int,
    email: String,
    year: Int,
    navController: NavHostController,
    context: Context
) {
    val api = TaxDeductionAPI.create()
    val request = InsertTaxDeductionClass(taxDeductionAmount, deductionTypeId, email, year)

    api.insertTaxDeduction(request).enqueue(object : Callback<TaxDeductionResponse> {
        override fun onResponse(call: Call<TaxDeductionResponse>, response: Response<TaxDeductionResponse>) {
            if (response.isSuccessful && response.body()?.success == 1) {
                Toast.makeText(context, "บันทึกข้อมูลค่าลดหย่อนสำเร็จ", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            } else {
                Toast.makeText(context, "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<TaxDeductionResponse>, t: Throwable) {
            Toast.makeText(context, "การเชื่อมต่อล้มเหลว: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}