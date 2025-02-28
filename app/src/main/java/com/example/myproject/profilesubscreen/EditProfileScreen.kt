package com.example.myproject.profilesubscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myproject.api.UserAPI
import com.example.myproject.database.UserClass
import com.example.myproject.loginandsignup.SharedPreferencesManager
import com.example.myproject.navigation.Screen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun EditScreen(navController: NavHostController? = null) {
    val context = LocalContext.current
    val sharedPreferences = remember { SharedPreferencesManager(context) }
    val userId = sharedPreferences.userId ?: 0
    val apiClient = UserAPI.create()

    // เก็บข้อมูลผู้ใช้
    var user by remember { mutableStateOf(UserClass(0, "", "", "", "")) }
    var fname by remember { mutableStateOf("") }
    var lname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    // โหลดข้อมูลผู้ใช้เมื่อเปิดหน้า
    LaunchedEffect(Unit) {
        apiClient.searchUser(userId).enqueue(object : Callback<UserClass> {
            override fun onResponse(call: Call<UserClass>, response: Response<UserClass>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        user = it
                        fname = it.fname
                        lname = it.lname
                        email = it.email
                        gender = it.gender
                    }
                } else {
                    Toast.makeText(context, "ไม่พบข้อมูลผู้ใช้", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UserClass>, t: Throwable) {
                Toast.makeText(context, "เกิดข้อผิดพลาด: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    // พื้นหลังสีเขียวด้านบน
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF00C09E))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Column(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 40.dp)
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "แก้ไขโปรไฟล์",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
            }

            // พื้นหลังสีขาวโค้งมน
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                color = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    EditTextField("ชื่อ", fname) { fname = it }
                    EditTextField("นามสกุล", lname) { lname = it }
                    EditTextField("อีเมล", email) { email = it }

                    // ตัวเลือกเพศ
                    GenderSelection(selectedGender = gender, onGenderSelected = { gender = it })

                    Spacer(modifier = Modifier.height(20.dp))

                    // ปุ่มยืนยัน
                    Button(
                        onClick = {
                            val updatedUser = UserClass(user.id, email, fname, lname, gender)
                            apiClient.updateUser(userId, updatedUser).enqueue(object : Callback<Void> {
                                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                    if (response.isSuccessful) {
                                        Toast.makeText(context, "อัปเดตข้อมูลสำเร็จ", Toast.LENGTH_LONG).show()
                                        navController?.navigate(Screen.Profile.route) // กลับไปหน้าโปรไฟล์
                                    } else {
                                        Toast.makeText(context, "อัปเดตข้อมูลไม่สำเร็จ", Toast.LENGTH_LONG).show()
                                    }
                                }

                                override fun onFailure(call: Call<Void>, t: Throwable) {
                                    Toast.makeText(context, "เกิดข้อผิดพลาด: ${t.message}", Toast.LENGTH_LONG).show()
                                }
                            })
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C09E)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("บันทึกการเปลี่ยนแปลง")
                    }

                    // ปุ่มยกเลิก
                    TextButton(
                        onClick = { navController?.popBackStack() },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("ยกเลิก", color = Color.Gray)
                    }
                }
            }
        }
    }
}


@Composable
fun GenderSelection(selectedGender: String, onGenderSelected: (String) -> Unit) {
    val genderOptions = listOf("ชาย" to "Male", "หญิง" to "Female", "อื่นๆ" to "Other")

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "เพศ",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            genderOptions.forEach { (label, value) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { onGenderSelected(value) }
                ) {
                    RadioButton(
                        selected = value == selectedGender,
                        onClick = { onGenderSelected(value) },
                        colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF00C09E))
                    )
                    Text(text = label)
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, color = Color(0xff757575)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color(0xFFE8F5F1),
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color(0xFF00C09E)
        ),
        shape = RoundedCornerShape(16.dp),
    )
}


