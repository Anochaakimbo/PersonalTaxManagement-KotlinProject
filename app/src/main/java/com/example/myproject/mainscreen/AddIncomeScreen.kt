package com.example.myproject.mainscreen

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.myproject.R

@Composable
fun AddIncomeScreen(navController: NavHostController) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "income_grid") {
        composable("income_grid") { IncomeGrid(navController) }
        composable("income_details/{incomeName}") { backStackEntry ->
            val incomeName = backStackEntry.arguments?.getString("incomeName") ?: ""
            IncomeDetailsScreen(navController, incomeName)
        }
    }
}

@Composable
fun IncomeGrid(navController: NavHostController) {
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
                    onClick = { navController.navigate("income_details/${name}") }
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
fun IncomeDetailsScreen(navController: NavHostController, incomeName: String) {
    var amount by remember { mutableStateOf("") }

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
            text = incomeName,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Input field for entering the amount
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("กรอกจำนวนเงิน") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Button to submit the amount
        Button(
            onClick = {
                // Handle submission
                // You can navigate back or send the value as needed
                navController.popBackStack()
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
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to go back without saving
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
                fontWeight = FontWeight.Bold
            )
        }
    }
}