package com.example.myproject.mainscreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.myproject.R
import com.example.myproject.api.UserAPI
import com.example.myproject.database.UserClass
import com.example.myproject.loginandsignup.SharedPreferencesManager
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfileScreen(navController: NavHostController,modifier: Modifier) {
    val contextForToast  = LocalContext.current.applicationContext
    lateinit var sharedPreferences : SharedPreferencesManager
    sharedPreferences = SharedPreferencesManager(contextForToast)
    val userId = sharedPreferences.userId ?: 0
    val createClient = UserAPI.create()
    val initialUser = UserClass(0,"","","","")
    var userItems by remember { mutableStateOf(initialUser) }
    var logoutDialog by remember { mutableStateOf(false) }
    var checkState by remember { mutableStateOf(false) }

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()


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
                                response.body()!!.gender)
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


    LazyColumn( // ใช้ LazyColumn ให้สามารถเลื่อนได้ทั้งหมด
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1FFF3)), // พื้นหลังของทั้งหน้า
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ส่วนหัวโปรไฟล์
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp) // ปรับความสูง
                    .background(Color(0xFF00BFA5)), // สีพื้นหลังส่วนหัว
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier.padding(top = 50.dp), // เลื่อนรูปลงมา
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile_user),
                            contentDescription = "Profile Picture",
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Box(
                        modifier = Modifier
                            .offset(x = 40.dp, y = -30.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(2.dp, Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit_text),
                            contentDescription = "Edit Profile",
                            tint = Color.Black,
                            modifier = Modifier.size(18.dp)
                        )
                    }
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
                )
            )
        }

        item {
            ProfileSection(
                title = "ความปลอดภัย",
                items = listOf(
                    "ความปลอดภัย" to R.drawable.shield,
                    "ธีม" to R.drawable.contrast
                )
            )
        }

        item {
            ProfileSection(
                title = "การช่วยเหลือ",
                items = listOf(
                    "การช่วยเหลือและสนับสนุน" to R.drawable.help,
                    "ติดต่อเรา" to R.drawable.conversation,
                    "ความเป็นส่วนตัว" to R.drawable.unlock
                )
            )
        }

        item {
            Spacer(modifier = Modifier.height(100.dp)) // ทำให้มีระยะห่างด้านล่าง
        }
    }
}

@Composable
fun ProfileSection(title: String, items: List<Pair<String, Int>>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)) // ปรับมุมโค้งให้มากขึ้น
            .border(BorderStroke(1.dp, Color.Gray), RoundedCornerShape(16.dp))
            .shadow(10.dp, RoundedCornerShape(16.dp)) // เงาที่เข้มขึ้น
            .background(Color.White)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            items.forEach { (text, icon) ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 8.dp)
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
                }
            }
        }
    }
}
