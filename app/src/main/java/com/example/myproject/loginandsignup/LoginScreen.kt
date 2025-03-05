package com.example.myproject.loginandsignup

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.myproject.R
import com.example.myproject.api.LoginAPI
import com.example.myproject.database.LoginClass
import com.example.myproject.navigation.Screen
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController, onLoginSuccess: () -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }
    val myFont = FontFamily(Font(R.font.khaidao))
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
            Lifecycle.State.RESUMED -> {
                if (sharedPreferences.isLoggedIn) {
                    navController.navigate(Screen.Home.route)
                } else if (!sharedPreferences.userEmail.isNullOrEmpty()) {
                    email = sharedPreferences.userEmail ?: "No email"
                }
            }
            else -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF00C09E))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top section with green background
            Column(
                modifier = Modifier
                    .padding(top = 40.dp, bottom = 40.dp)
                    .padding(horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Icon
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(Color.White, CircleShape)
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color(0xFF00C09E), CircleShape)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "ยินดีต้อนรับสู่ MyTaX",
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
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            isError = false
                        },
                        label = { Text("ชื่อบัญชีผู้ใช้/อีเมล") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFE8F5F1),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color(0xFF00C09E)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        isError = isError
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            isError = false
                        },
                        label = { Text("รหัสผ่าน") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color(0xFFE8F5F1),
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = Color(0xFF00C09E)
                        ),
                        shape = RoundedCornerShape(16.dp),
                        isError = isError
                    )

                    Text(
                        text = "ลืมรหัสผ่าน?",
                        color = Color(0xFF00C09E),
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { navController.navigate(Screen.ForgetPassword.route) }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            keyboardController?.hide()
                            focusManager.clearFocus()

                            isError = !validateInput(email, password)
                            if (!isError) {
                                createClient.loginUser(email, password).enqueue(object : Callback<LoginClass> {
                                    override fun onResponse(call: Call<LoginClass>, response: Response<LoginClass>) {
                                        response.body()?.let { loginResponse ->
                                            when (loginResponse.success) {
                                                1 -> {
                                                    sharedPreferences.isLoggedIn = true
                                                    sharedPreferences.userId = response.body()!!.id
                                                    sharedPreferences.userEmail = email
                                                    onLoginSuccess()
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
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00C09E)
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("เข้าสู่ระบบ")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("ไม่มีบัญชีการใช้งาน? ")
                        Text(
                            text = "สมัครสมาชิก",
                            color = Color(0xFF00C09E),
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.Register.route)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "หรือ เข้าสู่ระบบด้วย",
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedIconButton(
                            onClick = { /* Facebook login */ },
                            modifier = Modifier.size(48.dp),
                            border = BorderStroke(1.dp, Color.LightGray)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.account),
                                contentDescription = "Facebook Login",
                                tint = Color.Blue
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        OutlinedIconButton(
                            onClick = { /* Google login */ },
                            modifier = Modifier.size(48.dp),
                            border = BorderStroke(1.dp, Color.LightGray)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.account),
                                contentDescription = "Google Login",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun validateInput(email: String, password: String): Boolean {
    return email.isNotEmpty() && password.isNotEmpty()
}


