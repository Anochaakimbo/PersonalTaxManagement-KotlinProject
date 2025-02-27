package com.example.myproject.profilesubscreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.myproject.api.UserAPI
import com.example.myproject.database.PasswordChangeRequest
import com.example.myproject.loginandsignup.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SecureScreenViewModel : ViewModel() {
    var oldPassword by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    private val apiClient = UserAPI.create()

    fun changePassword(context: Context, userId: Int, navController: NavController) {
        if (newPassword != confirmPassword || newPassword.isEmpty()) {
            Toast.makeText(context, "รหัสผ่านใหม่และยืนยันรหัสผ่านไม่ตรงกัน!", Toast.LENGTH_SHORT).show()
            return
        }

        // ✅ ส่งค่า oldPassword ให้เซิร์ฟเวอร์ตรวจสอบ โดยไม่ต้องเปรียบเทียบเอง
        val request = PasswordChangeRequest(userId, oldPassword, newPassword)
        apiClient.changePassword(request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "เปลี่ยนรหัสผ่านสำเร็จ!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                } else {
                    Toast.makeText(context, "รหัสผ่านเก่าไม่ถูกต้อง!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(context, "เกิดข้อผิดพลาด: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

@Composable
fun SecureScreen(navController: NavController, viewModel: SecureScreenViewModel = SecureScreenViewModel()) {
    val context = LocalContext.current
    val sharedPreferences = remember { SharedPreferencesManager(context) }
    val userId = sharedPreferences.userId ?: 0 // ✅ ดึง userId จาก SharedPreferences

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
                    text = "เปลี่ยนรหัสผ่าน",
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
                    SecureTextField("รหัสผ่านเก่า", viewModel.oldPassword, { viewModel.oldPassword = it }, isPassword = true)
                    SecureTextField("รหัสผ่านใหม่", viewModel.newPassword, { viewModel.newPassword = it }, isPassword = true)
                    SecureTextField("ยืนยันรหัสผ่าน", viewModel.confirmPassword, { viewModel.confirmPassword = it }, isPassword = true)

                    Spacer(modifier = Modifier.height(20.dp))

                    // ปุ่มยืนยัน
                    Button(
                        onClick = { viewModel.changePassword(context, userId, navController) }, // ✅ ส่ง userId แทน NavController
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00C09E)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("เปลี่ยนรหัสผ่าน")
                    }

                    // ปุ่มยกเลิก
                    TextButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("ยกเลิก", color = Color.Gray)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecureTextField(label: String, value: String, onValueChange: (String) -> Unit, isPassword: Boolean = false) {
    var passwordVisible by remember { mutableStateOf(false) }

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
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (passwordVisible) "ซ่อนรหัสผ่าน" else "แสดงรหัสผ่าน"
                    )
                }
            }
        }
    )
}



