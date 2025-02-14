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
    var selectedOption by remember { mutableStateOf("") }
    var userInput by remember { mutableStateOf("") }

    when (currentScreen) {
        "options_grid" -> OptionsGrid { option ->
            selectedOption = option
            currentScreen = "income_screen"
        }
        "income_screen" -> IncomeScreen(selectedOption, userInput) { input ->
            userInput = input
            currentScreen = "options_grid"
            // Do something with userInput like saving to database or navigating
        }
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
            .padding(horizontal = 16.dp, vertical = 24.dp), // Adjust padding
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "เพิ่มค่าลดหย่อนที่มี",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp)) // Add spacing for aesthetics

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp) // Add spacing between rows
        ) {
            items.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly // Distribute buttons evenly
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
            .size(150.dp) // Adjust button size
            .clickable { onClick() }
            .padding(4.dp),
        shape = RoundedCornerShape(16.dp), // Round corners
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
                modifier = Modifier.size(56.dp) // Adjust icon size
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
fun IncomeScreen(optionName: String, userInput: String, onSave: (String) -> Unit) {
    var income by remember { mutableStateOf(userInput) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), // Adjust padding
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center // Center elements vertically
    ) {
        Text(
            text = optionName,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = income,
            onValueChange = { income = it },
            label = { Text("รายได้ (ก่อนถูกหักภาษี)") },
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onSave(income) },
            modifier = Modifier
                .width(140.dp) // Adjust button size
                .height(52.dp),
            shape = RoundedCornerShape(16.dp), // Round corners
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
    }
}