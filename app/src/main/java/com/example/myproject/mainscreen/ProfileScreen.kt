package com.example.myproject.mainscreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.bumptech.glide.Glide
import com.example.myproject.R
import com.example.myproject.api.UserAPI
import com.example.myproject.database.UploadResponse
import com.example.myproject.database.UserClass
import com.example.myproject.database.UserProfileResponse
import com.example.myproject.loginandsignup.SharedPreferencesManager
import com.example.myproject.navigation.Screen
import com.example.myproject.profilesubscreen.EditScreen
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


@Composable
fun ProfileScreen(navController: NavHostController, modifier: Modifier,onLogout: () -> Unit) {
    val contextForToast  = LocalContext.current.applicationContext
    lateinit var sharedPreferences : SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(contextForToast)
    var isLoggedIn by remember { mutableStateOf(sharedPreferences.isLoggedIn) }
    val userId = sharedPreferences.userId ?: 0
    val createClient = UserAPI.create()
    val initialUser = UserClass(0,"","","","")
    var userItems by remember { mutableStateOf(initialUser) }
    var notificationEnabled by remember { mutableStateOf(true) }
    var languageThai by remember { mutableStateOf(true) }
    var darkTheme by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var imageUrl by remember { mutableStateOf<String?>(null) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    var profileImageUri by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(lifecycleState) {
        when (lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {}
            Lifecycle.State.CREATED -> {}
            Lifecycle.State.STARTED -> {}
            Lifecycle.State.RESUMED -> {
                createClient.searchUser(userId).enqueue(object : Callback<UserClass> {
                    override fun onResponse(call : retrofit2.Call<UserClass>,
                                            response: Response<UserClass>) {
                        if (response.isSuccessful) {
                            userItems = UserClass(
                                response.body()!!.id,
                                response.body()!!.email,
                                response.body()!!.fname,
                                response.body()!!.lname,
                                response.body()!!.gender,
                                )
                        } else {
                            Toast.makeText(contextForToast, "DATA NOT FOUND", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<UserClass>, t: Throwable) {
                        Toast.makeText(contextForToast, "Error onFailure + t.message", Toast.LENGTH_LONG).show()
                    }
                })
            }
            else -> {}
        }

    }

    LaunchedEffect(Unit) {
        isLoggedIn = sharedPreferences.isLoggedIn
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            profileImageUri = it.toString()
            uploadProfileImage(context, userId, it) { newImageUrl ->
                imageUrl = newImageUrl // อัปโหลดไปยังเซิร์ฟเวอร์
            }
        }
    }

    // ✅ โหลดรูปเมื่อเปิดหน้าจอ
    LaunchedEffect(userId) {
        val createClient = UserAPI.create()
        createClient.getUserProfile(userId).enqueue(object : Callback<UserProfileResponse> {
            override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    imageUrl = response.body()!!.profile_image
                }
            }

            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                Toast.makeText(context, "โหลดรูปโปรไฟล์ล้มเหลว", Toast.LENGTH_SHORT).show()
            }
        })
    }


    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1FFF3)),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(Color(0xFF00BFA5)),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier.padding(top = 50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, Color.White, CircleShape)
                            .clickable { imagePickerLauncher.launch("image/*") }, // ✅ ใช้ MIME Type ที่ถูกต้อง
                        contentAlignment = Alignment.Center
                    ) {
                    if (!imageUrl.isNullOrEmpty()) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Profile Picture",
                                modifier = Modifier.fillMaxSize()
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.profile_user),
                                contentDescription = "Default Profile Picture",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }

                    Text(
                        text = "แตะเพื่อเปลี่ยนรูปโปรไฟล์",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${userItems.fname} ${userItems.lname}",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Text(
                        text = "${userItems.email}",
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 15.sp, letterSpacing = 0.25.sp)
                    )
                }
            }
        }

        // กล่องตั้งค่า
        item {
            ProfileSection(
                title = "ตั้งค่าทั่วไป",
                items = listOf(
                    "แก้ไขข้อมูลส่วนตัว" to R.drawable.id_card,
                    "การแจ้งเตือน" to R.drawable.bell,
                    "เปลี่ยนภาษา" to R.drawable.translate
                ),
                navController = navController,
                notificationEnabled = notificationEnabled,
                onNotificationToggle = { notificationEnabled = it },
                languageThai = languageThai,
                onLanguageToggle = { languageThai = it },
                darkTheme = darkTheme,
                onThemeToggle = { darkTheme = it }
            )
        }

        item {
            ProfileSection(
                title = "ความปลอดภัย",
                items = listOf(
                    "ความปลอดภัย" to R.drawable.shield,
                    "ธีม" to R.drawable.contrast
                ),
                navController = navController,
                notificationEnabled = notificationEnabled,
                onNotificationToggle = { notificationEnabled = it },
                languageThai = languageThai,
                onLanguageToggle = { languageThai = it },
                darkTheme = darkTheme,
                onThemeToggle = { darkTheme = it }

            )
        }

        item {
            ProfileSection(
                title = "การช่วยเหลือ",
                items = listOf(
                    "ติดต่อเรา" to R.drawable.conversation,
                    "ความเป็นส่วนตัว" to R.drawable.unlock
                ),
                navController = navController,
                notificationEnabled = notificationEnabled,
                onNotificationToggle = { notificationEnabled = it },
                languageThai = languageThai,
                onLanguageToggle = { languageThai = it },
                darkTheme = darkTheme,
                onThemeToggle = { darkTheme = it }
            )
        }



        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        isLoggedIn = false
                        sharedPreferences.clearUserLogin()
                        onLogout()
                        Log.d("DEBUG", "Logout สำเร็จ, เปลี่ยน isLoggedIn เป็น false")
                        Toast.makeText(contextForToast, "Logged out!", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C09E)
                    )
                ) {
                    Text(text = "Logout", fontSize = 18.sp)
                }
            }


            Spacer(modifier = Modifier.height(50.dp)) // เว้นช่องว่างล่างสุด
        }
    }
}

fun uploadProfileImage(context: Context, userId: Int, imageUri: Uri, onUploadSuccess: (String) -> Unit) {
    val createClient = UserAPI.create()
    val contentResolver = context.contentResolver
    val inputStream: InputStream? = contentResolver.openInputStream(imageUri)
    val file = File(context.cacheDir, "profile_image_${System.currentTimeMillis()}.jpg")

    inputStream?.use { input ->
        FileOutputStream(file).use { output ->
            input.copyTo(output)
        }
    } ?: run {
        Toast.makeText(context, "ไม่สามารถอ่านไฟล์รูปภาพ", Toast.LENGTH_SHORT).show()
        return
    }

    if (!file.exists() || file.length() == 0L) {
        Toast.makeText(context, "ไฟล์รูปภาพไม่ถูกต้อง", Toast.LENGTH_SHORT).show()
        return
    }

    val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
    val body = MultipartBody.Part.createFormData("profile_image", file.name, requestFile)

    createClient.uploadProfileImage(userId, body).enqueue(object : Callback<UploadResponse> {
        override fun onResponse(call: Call<UploadResponse>, response: Response<UploadResponse>) {
            if (response.isSuccessful && response.body()?.success == true) {
                Toast.makeText(context, "อัปโหลดสำเร็จ!", Toast.LENGTH_SHORT).show()

                // ✅ โหลด URL รูปใหม่แล้วอัปเดต UI ทันที
                createClient.getUserProfile(userId).enqueue(object : Callback<UserProfileResponse> {
                    override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                        if (response.isSuccessful && response.body() != null) {
                            val newImageUrl = response.body()!!.profile_image
                            onUploadSuccess(newImageUrl) // ✅ ส่งค่า URL ใหม่ไปอัปเดต imageUrl
                        }
                    }

                    override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                        Toast.makeText(context, "โหลดรูปโปรไฟล์ใหม่ล้มเหลว", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(context, " ${response.message()}", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
            Toast.makeText(context, "ข้อผิดพลาด: ${t.message}", Toast.LENGTH_SHORT).show()
        }
    })
}

fun loadProfileImage(context: Context, userId: Int, imageView: ImageView) {
    val createClient = UserAPI.create()

    createClient.getUserProfile(userId).enqueue(object : Callback<UserProfileResponse> {
        override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
            if (response.isSuccessful && response.body() != null) {
                val imageUrl = response.body()!!.profile_image

                // ✅ โหลดรูปจาก URL เข้า ImageView
                Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.profile_user) // รูป Placeholder
                    .error(R.drawable.profile_user) // รูปแสดงเมื่อโหลดไม่สำเร็จ
                    .into(imageView)
            }
        }

        override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
            Toast.makeText(context, "โหลดรูปโปรไฟล์ล้มเหลว", Toast.LENGTH_SHORT).show()
        }
    })
}



@Composable
fun ProfileSection(title: String, items: List<Pair<String, Int>>,
                   navController: NavHostController ,
                   notificationEnabled: Boolean,
                   onNotificationToggle: (Boolean) -> Unit,
                   languageThai: Boolean,
                    onLanguageToggle: (Boolean) -> Unit,
                    darkTheme: Boolean,
                   onThemeToggle: (Boolean) -> Unit)
{
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(16.dp))
            .shadow(10.dp, RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        items.forEach { (text, icon) ->
            TextButton(
                onClick = {
                    when (text) {
                        "แก้ไขข้อมูลส่วนตัว" -> navController.navigate(Screen.EditProfileScreen.route)
                        "เปลี่ยนภาษา" -> navController.navigate("language_screen")
                        "ความปลอดภัย" -> navController.navigate(Screen.SecureScreen.route)
                        "ธีม" -> navController.navigate("theme_screen")
                        "ติดต่อเรา" -> navController.navigate(Screen.ContactScreen.route)
                        "ความเป็นส่วนตัว" -> navController.navigate(Screen.PrivacyScreen.route)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start) // ทำให้ปุ่มชิดซ้าย
                    .padding(vertical = 4.dp)
                    .height(50.dp),

                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.textButtonColors(containerColor = Color.Transparent)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = text,
                        color = Color.Black,
                        style = TextStyle(fontSize = 16.sp)
                    )
                    if (text == "การแจ้งเตือน") {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(
                            onClick = {
                                val newState = !notificationEnabled
                                onNotificationToggle(newState)

                                // ✅ แสดง Toast ตามสถานะใหม่
                                val message = if (newState) "เปิดการแจ้งเตือน" else "ปิดการแจ้งเตือน"
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Text(
                                text = if (notificationEnabled) "เปิด" else "ปิด",
                                color = Color.Blue
                            )
                        }
                    }
                    if (text == "เปลี่ยนภาษา") {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = { onLanguageToggle(!languageThai) }) {
                            Text(text = if (languageThai) "ไทย" else "English", color = Color.Blue)
                        }
                    }

                    if (text == "ธีม") {
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(onClick = { onThemeToggle(!darkTheme) }) {
                            Text(text = if (darkTheme) "มืด" else "สว่าง", color = Color.Blue)
                        }
                    }
                }
            }
        }
    }
}