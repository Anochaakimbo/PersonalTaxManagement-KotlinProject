package com.example.myproject.mainscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myproject.R

@Composable
fun HomeScreen(navController: NavHostController) {
    val contextForToast = LocalContext.current.applicationContext

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .align(Alignment.BottomCenter)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp)
                )
        )

        Box(
            modifier = Modifier
                .size(width = 300.dp, height = 250.dp)
                .align(Alignment.Center)
                .background(
                    color = Color(0xFF00695C),
                    shape = RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center


            ) {
                Image(
                    painter = painterResource(id = R.drawable.money_transfer_test),
                    contentDescription = "Tax Icon",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 8.dp),
                )
                Text(
                    text = "เริ่มคำนวณภาษี",
                    color = Color.White,
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        Toast.makeText(contextForToast, "เริ่มคำนวณภาษี", Toast.LENGTH_SHORT).show()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier.clip(RoundedCornerShape(10.dp))
                ) {
                    Text(
                        text = "START",
                        color = Color(0xFF00695C),
                        fontSize = 16.sp
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .align(Alignment.BottomCenter)
                .padding(top = 10.dp)
                .background(
                    color = Color(0xFFF5F5DC), // สีครีม
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                )
        )

        Scaffold(
            bottomBar = { MyBottomBar(navController, contextForToast) },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) { }
        }
    }
}
