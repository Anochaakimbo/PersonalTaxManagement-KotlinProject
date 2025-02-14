package com.example.myproject.loginandsignup

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.myproject.api.LoginAPI
import com.example.myproject.database.LoginClass
import com.example.myproject.navigation.Screen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun LoginScreen(navController: NavHostController,onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val createClient = LoginAPI.create()
    val contextForToast = LocalContext.current.applicationContext
    lateinit var sharedPreferences: SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(context = contextForToast)
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current


    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> { // ใช้ RESUMED state เท่านั้น
                if (sharedPreferences.isLoggedIn) {
                    navController.navigate(Screen.Home.route)
                } else if (!sharedPreferences.userEmail.isNullOrEmpty()) {
                    email = sharedPreferences.userEmail ?: "No email"
                }
            }
            else -> {}
        }
    }

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
                    createClient.loginUser(email, password).enqueue(object : Callback<LoginClass> {
                        override fun onResponse(call: Call<LoginClass>, response: Response<LoginClass>) {
                            response.body()?.let { loginResponse ->
                                when (loginResponse.success) {
                                    1 -> {
                                        sharedPreferences.isLoggedIn = true
                                        sharedPreferences.userId = response.body()!!.id // ✅ บันทึกเป็น String
                                        sharedPreferences.userEmail = email

                                        Toast.makeText(contextForToast, "Login successful : ${response.body()!!.id}", Toast.LENGTH_LONG).show()
                                        onLoginSuccess() // ✅ อัปเดตสถานะล็อกอินใน `MainActivity.kt`
                                        navController.navigate(Screen.Home.route)
                                    }
                                    else -> {
                                        Toast.makeText(contextForToast, "Email or password is incorrect.", Toast.LENGTH_LONG).show()
                                    }
                                }
                            } ?: run {
                                Toast.makeText(contextForToast, "Login failed. Please try again.", Toast.LENGTH_LONG).show()
                            }
                        }
                        override fun onFailure(call: Call<LoginClass>, t: Throwable) {
                            Toast.makeText(contextForToast, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })
                }
            }
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



