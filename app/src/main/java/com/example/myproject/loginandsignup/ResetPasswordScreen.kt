package com.example.myproject.loginandsignup

import com.example.myproject.navigation.Screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import android.widget.Toast
import com.example.myproject.api.UserAPI
import com.example.myproject.database.ResetPasswordRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(navController: NavHostController, email: String?) {
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    val userApi = UserAPI.create()
    val contextForToast = LocalContext.current.applicationContext
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "รีเซ็ตรหัสผ่าน", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Email (แก้ไขไม่ได้)
        OutlinedTextField(
            value = email ?: "",
            onValueChange = {},
            label = { Text("อีเมล") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFE8F5F1),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color(0xFF00C09E)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // New Password
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("รหัสผ่านใหม่") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = passwordError,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFE8F5F1),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color(0xFF00C09E)
            )
        )

        if (passwordError) {
            Text(
                text = "รหัสผ่านต้องมีอย่างน้อย 6 ตัวอักษร",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("ยืนยันรหัสผ่าน") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = confirmPasswordError,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = Color(0xFFE8F5F1),
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color(0xFF00C09E)
            )
        )

        if (confirmPasswordError) {
            Text(
                text = "รหัสผ่านไม่ตรงกัน",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.align(Alignment.Start)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Submit Button
        Button(
            onClick = {
                keyboardController?.hide()

                passwordError = newPassword.length < 6
                confirmPasswordError = newPassword != confirmPassword

                if (!passwordError && !confirmPasswordError) {
                    val request = ResetPasswordRequest(email!!, newPassword) // เชื่อว่า email ต้องมีค่าจริงแล้ว

                    userApi.resetPassword(request).enqueue(object : Callback<ResetPasswordRequest> {
                        override fun onResponse(call: Call<ResetPasswordRequest>, response: Response<ResetPasswordRequest>) {
                            if (response.isSuccessful) {
                                Toast.makeText(contextForToast, "เปลี่ยนรหัสผ่านสำเร็จ!", Toast.LENGTH_LONG).show()
                                navController.navigate(Screen.Login.route) // 🔥 กลับไปหน้า Login
                            } else {
                                Toast.makeText(contextForToast, "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun onFailure(call: Call<ResetPasswordRequest>, t: Throwable) {
                            Toast.makeText(contextForToast, "เชื่อมต่อเซิร์ฟเวอร์ไม่ได้!", Toast.LENGTH_LONG).show()
                        }
                    })
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C09E))
        ) {
            Text("ยืนยันการเปลี่ยนรหัสผ่าน")
        }
    }
}
