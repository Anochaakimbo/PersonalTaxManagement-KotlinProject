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
            if (selectedOption == "ประกันสังคม") {
                currentScreen = "income_screen"
            }
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "เพิ่ม\nค่าลดหย่อนที่มี",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column {
            items.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    rowItems.forEach { (text, icon) ->
                        OptionCard(
                            text = text,
                            iconRes = icon,
                            onClick = { onOptionSelected(text) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun OptionCard(text: String, iconRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = text,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
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
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
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
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onBack() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("กลับ", color = MaterialTheme.colorScheme.onPrimary, fontSize = 18.sp)
        }
    }
}
