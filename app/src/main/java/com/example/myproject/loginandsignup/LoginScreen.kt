package com.example.myproject.loginandsignup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ยินดีต้อนรับสู่ MyTaX",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isError = false
            },
            label = { Text("ชื่อผู้ใช้/อีเมลล์") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            isError = isError
        )

        PasswordTextField(password = password,
            onPasswordChange = {
                password = it
                isError = false
            },
            modifier = Modifier.padding(bottom = 5.dp),
            isError = isError)

        Row(
            modifier = Modifier.padding(top = 1.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Text(
                text = "ลืมรหัสผ่าน",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary,  // หรือ Color.Green
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true)
                    ) {
                        navController.navigate("forgetpassword")
                    }
                    .padding(4.dp)
            )
        }

        Spacer(modifier = Modifier.size(30.dp))

        Button(
            onClick = {
                isError = !validateInput(email, password)
                if (!isError) {
                    navController.navigate("main")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("เข้าสู่ระบบ")
        }

        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("ไม่มีบัญชีการใช้งาน?",
                fontSize = 15.sp,)
            Text(
                text = "สมัครสมาชิก",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary,  // หรือ Color.Green
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true)
                    ) {
                        navController.navigate("register")
                    }
                    .padding(4.dp)
            )
        }
    }
}


private fun validateInput(email: String, password: String): Boolean {
    return email.isNotEmpty() && password.isNotEmpty()
}



