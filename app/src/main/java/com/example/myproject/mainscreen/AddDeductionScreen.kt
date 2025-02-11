package com.example.myproject.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myproject.R

@Composable
fun TaxDeductionScreen(navController: NavHostController) {
    var currentScreen by remember { mutableStateOf("options_grid") }

    when (currentScreen) {
        "options_grid" -> OptionsGrid { selectedOption ->
            currentScreen = "income_screen" // เปลี่ยนหน้าตามที่เลือก
        }
        "income_screen" -> IncomeScreen { currentScreen = "options_grid" }
    }
}

@Composable
fun OptionsGrid(onOptionSelected: (String) -> Unit) {
    val items = listOf(
        "ประกันสังคม" to R.drawable.account,
        "Easy E-Receipt" to R.drawable.receipt,
        "ดอกเบี้ยบ้าน" to R.drawable.home_24,
        "ประกันชีวิตทั่วไป" to R.drawable.shield,
        "บริจาคทั่วไป" to R.drawable.stars,
        "Thai ESG" to R.drawable.compost
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 24.dp), // ปรับระยะขอบให้พอดี
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "เพิ่ม\nค่าลดหย่อนที่มี",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp)) // เพิ่มระยะห่างให้ดูสวยขึ้น

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp) // เพิ่มช่องว่างระหว่างแถว
        ) {
            items.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly // กระจายปุ่มให้สมดุล
                ) {
                    rowItems.forEach { (text, icon) ->
                        OptionCard(
                            text = text,
                            iconRes = icon,
                            onClick = { onOptionSelected(text) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OptionCard(text: String, iconRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .size(150.dp) // ปรับให้ปุ่มมีขนาดพอดี
            .clickable { onClick() }
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp), // ปรับให้โค้งมนมากขึ้น
        elevation = CardDefaults.elevatedCardElevation(6.dp),
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
                modifier = Modifier.size(56.dp) // ปรับขนาดไอคอนให้พอดี
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun IncomeScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), // ปรับระยะให้ดูดีขึ้น
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // ทำให้ปุ่มอยู่ตรงกลางหน้าจอ
    ) {
        Text(
            text = "เงินเดือน และโบนัส",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "รายได้\n(ก่อนถูกหักภาษี)",
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "฿",
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onBack() },
            modifier = Modifier
                .width(140.dp) // ปรับขนาดปุ่มให้พอดี
                .height(52.dp),
            shape = RoundedCornerShape(16.dp), // ทำให้มนมากขึ้น
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Text(
                text = "กลับ",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
