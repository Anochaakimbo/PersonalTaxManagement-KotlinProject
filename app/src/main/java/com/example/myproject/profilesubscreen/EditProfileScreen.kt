package com.example.myproject.profilesubscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.ui.tooling.preview.Preview
import com.example.myproject.R
import com.example.myproject.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(navController: NavHostController? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xfff1fff3))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "แก้ไขโปรไฟล์",
                color = Color.Black,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(20.dp))

            EditTextField("ชื่อ - นามสกุล" )
            EditTextField("อีเมล" )
            EditTextField("เบอร์โทรศัพท์")
            EditTextField("เพศ" )
            EditTextField("ที่อยู่")

            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { navController?.navigate(Screen.Profile.route) }) {
                Text(text = "ยืนยัน")
            }
            Button(onClick = { navController?.popBackStack() }, colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)) {
                Text(text = "ยกเลิก")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextField(label: String) {
    OutlinedTextField(
        value = "",
        onValueChange = {},
        label = { Text(text = label, color = Color(0xff757575)) },
        textStyle = TextStyle(fontSize = 16.sp, letterSpacing = 0.25.sp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Color(0xff212121), // สีเมื่อมีโฟกัส
            unfocusedTextColor = Color(0xff212121), // สีเมื่อไม่มีโฟกัส
            disabledTextColor = Color.Gray, // สีเมื่อถูกปิดใช้งาน
            containerColor = Color(0xfff3f8ff)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp, vertical = 8.dp)
    )
}

