package com.example.myproject.loginandsignup



import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myproject.api.RegisterAPI
import com.example.myproject.database.UserClass
import com.example.myproject.navigation.Screen
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun RegisterScreen(navController: NavHostController
) {
    val contextForToast = LocalContext.current
    val createClient = RegisterAPI.create()
    var fname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var lname by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    var selectedGender by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "สมัครสมาชิก",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

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
            isError = isError
        )

        PasswordTextField(password = password,
            onPasswordChange = {
                password = it
                isError = false
            },
            modifier = Modifier.padding(bottom = 24.dp),
            isError = isError)

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                isError = false
            },
            label = { Text("ยืนยันรหัสผ่าน") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            isError = isError
        )
        Text(text = "Gender")
        Row {
            RadioButton(
                selected = selectedGender == "Male",
                onClick = { selectedGender = "Male" }
            )
            Text("Male")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = selectedGender == "Female",
                onClick = { selectedGender = "Female" }
            )
            Text("Female")
            Spacer(modifier = Modifier.width(8.dp))
            RadioButton(
                selected = selectedGender == "Other",
                onClick = { selectedGender = "Other" }
            )
            Text("Other")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isError = !validateRegistrationInput(fname,lname, email, password, confirmPassword)
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
                                navController.navigate(Screen.Login.route) // เปลี่ยนหน้าไปยัง LoginScreen
                            } else {
                                Toast.makeText(contextForToast, "Inserted Failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                        override fun onFailure(call:Call<UserClass>,t:Throwable){
                            Toast.makeText(contextForToast, "Error onFailure" + t.message , Toast.LENGTH_LONG).show()
                        }
                    })
                }
                    navController.navigate(Screen.Login.route)
                }
        ) {
            Text("สมัครสมาชิก")
        }

        Row(
            modifier = Modifier.padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("มีบัญชีใช้งานอยู่แล้ว?",
                fontSize = 15.sp,)
            Text(
                text = "เข้าสู่ระบบ",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.primary,  // หรือ Color.Green
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = true)
                    ) {
                        navController.navigate(Screen.Login.route)
                    }
                    .padding(4.dp)
            )
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

@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    isError: Boolean = false
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("รหัสผ่าน") },
        modifier = modifier.fillMaxWidth(),
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        trailingIcon = {
            IconButton(
                onClick = { passwordVisible = !passwordVisible }
            ) {
                Icon(
                    imageVector = if (passwordVisible) {
                        Icons.Default.Close
                    } else {
                        Icons.Default.Star
                    },
                    contentDescription = if (passwordVisible) {
                        "ซ่อนรหัสผ่าน"
                    } else {
                        "แสดงรหัสผ่าน"
                    }
                )
            }
        },
        isError = isError
    )
}