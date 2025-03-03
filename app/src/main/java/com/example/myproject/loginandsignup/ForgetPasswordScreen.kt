package com.example.myproject.loginandsignup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myproject.R
import com.example.myproject.navigation.Screen
import android.widget.Toast
import com.example.myproject.api.UserAPI
import com.example.myproject.database.SendOtpRequest
import com.example.myproject.database.VerifyOtpRequest
import com.example.myproject.database.VerifyOtpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var otpError by remember { mutableStateOf(false) }
    var otpSent by remember { mutableStateOf(false) } // เช็คว่าส่ง OTP ไปหรือยัง
    val userApi = UserAPI.create()
    val myFont = FontFamily(Font(R.font.khaidao))
    val contextForToast = LocalContext.current.applicationContext
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF00C09E))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top section with back button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .background(Color.White.copy(alpha = 0.3f), CircleShape)
                        .size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "กลับ",
                        tint = Color.White
                    )
                }
            }

            // Title
            Column(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 40.dp)
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "ลืมรหัสผ่าน",
                    style = MaterialTheme.typography.headlineMedium.copy(fontFamily = myFont),
                    color = Color.White
                )
            }

            // White Container
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
                    Text(
                        text = "กรุณากรอกอีเมลที่ใช้ลงทะเบียน\nเพื่อรับ OTP สำหรับรีเซ็ตรหัสผ่าน",
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Email Input
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            isError = false
                        },
                        label = { Text("อีเมล") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFE8F5F1),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color(0xFF00C09E)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        isError = isError,
                        singleLine = true
                    )

                    if (isError) {
                        Text(
                            text = "กรุณากรอกอีเมลให้ถูกต้อง",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 8.dp, bottom = 8.dp)
                        )
                    }

                    // OTP Input (แสดงเฉพาะเมื่อส่ง OTP แล้ว)
                    if (otpSent) {
                        OutlinedTextField(
                            value = otp,
                            onValueChange = {
                                otp = it
                                otpError = false
                            },
                            label = { Text("รหัส OTP") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color(0xFFE8F5F1),
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color(0xFF00C09E)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            isError = otpError,
                            singleLine = true
                        )

                        if (otpError) {
                            Text(
                                text = "กรุณากรอก OTP ให้ถูกต้อง",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(start = 8.dp, bottom = 8.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            focusManager.clearFocus()

                            if (!otpSent) {
                                // 📌 กรณี: ส่ง OTP
                                isError = !validateInput(email)
                                if (!isError) {
                                    val request = SendOtpRequest(email)

                                    userApi.sendOtp(request)
                                        .enqueue(object : Callback<SendOtpRequest> {
                                            override fun onResponse(
                                                call: Call<SendOtpRequest>,
                                                response: Response<SendOtpRequest>
                                            ) {
                                                if (response.isSuccessful) {
                                                    Toast.makeText(
                                                        contextForToast,
                                                        "ส่ง OTP ไปยัง $email แล้ว",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                    otpSent = true // เปิดช่องกรอก OTP
                                                } else {
                                                    Toast.makeText(
                                                        contextForToast,
                                                        "เกิดข้อผิดพลาดในการส่ง OTP",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<SendOtpRequest>,
                                                t: Throwable
                                            ) {
                                                Toast.makeText(
                                                    contextForToast,
                                                    "เชื่อมต่อเซิร์ฟเวอร์ไม่ได้!",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        })
                                }
                            } else {
                                // 📌 กรณี: ยืนยัน OTP และพาไปหน้าเปลี่ยนรหัสผ่าน
                                otpError = otp.isEmpty()

                                if (!otpError) {
                                    val request = VerifyOtpRequest(email, otp)

                                    userApi.verifyOtp(request)
                                        .enqueue(object : Callback<VerifyOtpResponse> {
                                            override fun onResponse(
                                                call: Call<VerifyOtpResponse>,
                                                response: Response<VerifyOtpResponse>
                                            ) {
                                                if (response.isSuccessful) {
                                                    val responseBody = response.body()
                                                    if (responseBody != null && responseBody.success == 1) {
                                                        Toast.makeText(
                                                            contextForToast,
                                                            "OTP ถูกต้อง!",
                                                            Toast.LENGTH_LONG
                                                        ).show()

                                                        navController.navigate(
                                                            Screen.ResetPassword.withArgs(
                                                                email
                                                            )
                                                        )
                                                    } else {
                                                        Toast.makeText(
                                                            contextForToast,
                                                            "OTP ไม่ถูกต้อง หรือหมดอายุ",
                                                            Toast.LENGTH_LONG
                                                        ).show()
                                                    }
                                                } else {
                                                    Toast.makeText(
                                                        contextForToast,
                                                        "เกิดข้อผิดพลาดในการตรวจสอบ OTP",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<VerifyOtpResponse>,
                                                t: Throwable
                                            ) {
                                                Toast.makeText(
                                                    contextForToast,
                                                    "เชื่อมต่อเซิร์ฟเวอร์ไม่ได้!",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        })
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00C09E)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(if (otpSent) "ยืนยัน OTP" else "ส่ง OTP")
                    }

                }
            }
        }
    }
}

private fun validateInput(email: String): Boolean {
    return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}
