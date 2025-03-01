package com.example.myproject.loginandsignup



import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myproject.R
import com.example.myproject.api.RegisterAPI
import com.example.myproject.database.UserClass
import com.example.myproject.navigation.Screen
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {
    val contextForToast = LocalContext.current
    val createClient = RegisterAPI.create()
    var fname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var lname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    val myFont = FontFamily(Font(R.font.ibmplexsansthai_regular))

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
                    text = "สมัครสมาชิก",
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
                        value = fname,
                        onValueChange = {
                            fname = it
                            isError = false
                        },
                        label = { Text("ชื่อจริง") },
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
                        value = lname,
                        onValueChange = {
                            lname = it
                            isError = false
                        },
                        label = { Text("นามสกุล") },
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
                        value = email,
                        onValueChange = {
                            email = it
                            isError = false
                        },
                        label = { Text("อีเมลล์") },
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
                        value = confirmPassword,
                        onValueChange = {
                            confirmPassword = it
                            isError = false
                        },
                        label = { Text("ยืนยันรหัสผ่าน") },
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(
                                    imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                                )
                            }
                        },
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

                    Text(
                        text = "เพศ",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(bottom = 8.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { selectedGender = "Male" }
                        ) {
                            RadioButton(
                                selected = selectedGender == "Male",
                                onClick = { selectedGender = "Male" },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF00C09E))
                            )
                            Text("ชาย")
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { selectedGender = "Female" }
                        ) {
                            RadioButton(
                                selected = selectedGender == "Female",
                                onClick = { selectedGender = "Female" },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF00C09E))
                            )
                            Text("หญิง")
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { selectedGender = "Other" }
                        ) {
                            RadioButton(
                                selected = selectedGender == "Other",
                                onClick = { selectedGender = "Other" },
                                colors = RadioButtonDefaults.colors(selectedColor = Color(0xFF00C09E))
                            )
                            Text("อื่นๆ")
                        }
                    }

                    Button(
                        onClick = {
                            isError = !validateRegistrationInput(fname, lname, email, password, confirmPassword)
                            if (!isError) {
                                createClient.insertUser(
                                    email,
                                    password,
                                    fname,
                                    lname,
                                    selectedGender
                                ).enqueue(object : Callback<UserClass> {
                                    override fun onResponse(
                                        call: Call<UserClass>,
                                        response: Response<UserClass>
                                    ) {
                                        if (response.isSuccessful) {
                                            Toast.makeText(contextForToast, "Successfully Inserted", Toast.LENGTH_SHORT).show()
                                            navController.navigate(Screen.Login.route)
                                        } else {
                                            Toast.makeText(contextForToast, "Inserted Failed", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    override fun onFailure(call: Call<UserClass>, t: Throwable) {
                                        Toast.makeText(contextForToast, "Error onFailure" + t.message, Toast.LENGTH_LONG).show()
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
                        Text(text ="สมัครสมาชิก",
                            style = MaterialTheme.typography.headlineMedium.copy(fontFamily = myFont),)

                    }

                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text("มีบัญชีใช้งานอยู่แล้ว? ")
                        Text(
                            text = "เข้าสู่ระบบ",
                            color = Color(0xFF00C09E),
                            modifier = Modifier.clickable {
                                navController.navigate(Screen.Login.route)
                            }
                        )
                    }
                }
            }
        }
    }
}

private fun validateRegistrationInput(
    fname: String,
    lname: String,
    email: String,
    password: String,
    confirmPassword: String
): Boolean {
    return fname.isNotEmpty() &&
            lname.isNotEmpty() &&
            email.isNotEmpty() &&
            password.isNotEmpty() &&
            confirmPassword.isNotEmpty() &&
            password == confirmPassword
}
