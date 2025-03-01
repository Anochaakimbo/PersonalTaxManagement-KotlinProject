package com.example.myproject.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myproject.navigation.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaxButtomSheet(navConroller: NavHostController, onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                onDismiss()
            }
        },
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "เพิ่มรายได้ / ค่าลดหย่อน",
                fontSize = 20.sp,
                color = Color.Black,
                )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {  navConroller.navigate(Screen.AddIncome.route) },
                    modifier = Modifier.weight(1f).padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDAF7A6))
                ) {
                    Text("รายได้", color = Color.Black,
                        style = MaterialTheme.typography.titleLarge,)
                }

                Button(
                    onClick = {
                        navConroller.navigate(Screen.TaxDeduction.route)
                    },
                    modifier = Modifier.weight(1f).padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFA6E1FA))
                ) {
                    Text("ค่าลดหย่อน", color = Color.Black,
                        style = MaterialTheme.typography.titleLarge,)
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}
