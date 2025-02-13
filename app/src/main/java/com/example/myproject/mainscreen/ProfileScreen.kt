package com.example.myproject.mainscreen

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.example.myproject.R
import com.example.myproject.api.UserAPI
import com.example.myproject.components.TopAppBar
import com.example.myproject.database.ProfileClass
import com.example.myproject.loginandsignup.SharedPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ProfileScreen(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(navController = navController) // ✅ ใส่ TopAppBar
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .requiredWidth(width = 440.dp)
                .requiredHeight(height = 956.dp)
                .background(color = Color(0xfff1fff3))

        ) {
            Box(
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(
                        x = 73.dp,
                        y = 110.dp
                    )
                    .requiredWidth(width = 294.dp)
                    .requiredHeight(height = 200.dp)
            ) {
                Box(
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .offset(
                            x = 76.dp,
                            y = 0.dp
                        )
                        .requiredWidth(width = 141.dp)
                        .requiredHeight(height = 142.dp)
                ) {
                    KindAvatar6()
                    Box(
                        modifier = Modifier
                            .align(alignment = Alignment.TopStart)
                            .offset(
                                x = 89.9290771484375.dp,
                                y = 91.75390625.dp
                            )
                            .requiredWidth(width = 51.dp)
                            .requiredHeight(height = 50.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .requiredWidth(width = 51.dp)
                                .requiredHeight(height = 50.dp)
                                .clip(shape = CircleShape)
                                .background(color = Color(0xfff5f5f5))
                                .border(
                                    border = BorderStroke(5.dp, Color.White),
                                    shape = CircleShape
                                )
                        )
                        Image(
                            painter = painterResource(id = R.drawable.edit_text),
                            contentDescription = "line/design/edit-line",
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .offset(
                                    x = 12.2125244140625.dp,
                                    y = 12.015380859375.dp
                                )
                                .requiredWidth(width = 27.dp)
                                .requiredHeight(height = 26.dp)
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .align(alignment = Alignment.TopCenter)
                        .offset(y = 147.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "นายดุ่ย ดันดาดัน",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 0.88.em,
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier
                            .requiredWidth(width = 250.dp)
                    )
                    Text(
                        text = "Dui@gmail.com | +01 234 567 89",
                        color = Color.Black,
                        textAlign = TextAlign.Center,
                        lineHeight = 1.33.em,
                        style = TextStyle(
                            fontSize = 15.sp,
                            letterSpacing = 0.25.sp
                        ),
                        modifier = Modifier
                            .requiredWidth(width = 250.dp)
                    )
                }
            }
            Box(
                modifier = modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 24.dp, y = 373.dp)
                    .requiredWidth(width = 391.dp)
                    .requiredHeight(height = 513.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .requiredWidth(width = 391.dp)
                            .requiredHeight(height = 165.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .requiredWidth(width = 391.dp)
                                .requiredHeight(height = 165.dp)
                                .clip(shape = RoundedCornerShape(8.dp))
                                .background(color = Color.White)
                        )
                        Component11(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .offset(x = 18.dp, y = 19.dp)
                        )
                        Component12(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .offset(x = 18.dp, y = 66.dp)
                        )
                        Component13(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .offset(x = 18.dp, y = 117.dp)
                        )
                    }
                    // กล่องที่สอง
                    Box(
                        modifier = Modifier
                            .requiredWidth(width = 391.dp)
                            .requiredHeight(height = 165.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .requiredWidth(width = 391.dp)
                                .requiredHeight(height = 165.dp)
                                .clip(shape = RoundedCornerShape(8.dp))
                                .background(color = Color.White)
                        )
                        Component16(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .offset(x = 18.dp, y = 19.dp)
                        )
                        Component17(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .offset(x = 18.dp, y = 66.dp)
                        )
                        Component18(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .offset(x = 18.dp, y = 117.dp)
                        )
                    }
                    // กล่องที่สาม
                    Box(
                        modifier = Modifier
                            .requiredWidth(width = 391.dp)
                            .requiredHeight(height = 117.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .requiredWidth(width = 391.dp)
                                .requiredHeight(height = 117.dp)
                                .clip(shape = RoundedCornerShape(8.dp))
                                .background(color = Color.White)
                        )
                        Component14(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .offset(x = 18.dp, y = 19.dp)
                        )
                        Component15(
                            modifier = Modifier
                                .align(alignment = Alignment.TopStart)
                                .offset(x = 18.dp, y = 66.dp)
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun KindAvatar6(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 133.dp)
            .requiredHeight(height = 131.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_user),
            contentDescription = "Rectangle 6",
            modifier = Modifier
                .fillMaxSize())
    }
}

@Composable
fun Component11(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 317.dp)
            .requiredHeight(height = 33.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.id_card),
            contentDescription = "line/health/mental-health-line")
        Text(
            text = "แก้ไขข้อมูลส่วนตัว",
            color = Color.Black,
            lineHeight = 1.33.em,
            style = TextStyle(
                fontSize = 15.sp,
                letterSpacing = 0.25.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 45.dp, y = 5.dp)
                .fillMaxHeight()
                .requiredWidth(width = 240.dp))
    }
}

@Composable
fun Component12(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 317.dp)
            .requiredHeight(height = 33.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.bell),
            contentDescription = "line/media/notification-3-line")
        Text(
            text = "การแจ้งเตือน",
            color = Color.Black,
            lineHeight = 1.33.em,
            style = TextStyle(
                fontSize = 15.sp,
                letterSpacing = 0.25.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 45.dp,
                    y = 5.dp)
                .fillMaxHeight()
                .requiredWidth(width = 240.dp))
        Text(
            text = "เปิด",
            color = Color(0xff1573fe),
            textAlign = TextAlign.End,
            lineHeight = 1.43.em,
            style = TextStyle(
                fontSize = 14.sp,
                letterSpacing = 0.25.sp),
            modifier = Modifier
                .fillMaxSize()
                .offset( y = 6.dp))
    }
}

@Composable
fun Component13(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 317.dp)
            .requiredHeight(height = 33.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.translate),
            contentDescription = "line/media/notification-3-line")
        Text(
            text = "เปลี่ยนภาษา",
            color = Color.Black,
            lineHeight = 1.33.em,
            style = TextStyle(
                fontSize = 15.sp,
                letterSpacing = 0.25.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 45.dp,
                    y = 5.dp)
                .fillMaxHeight()
                .requiredWidth(width = 240.dp))
        Text(
            text = "ไทย",
            color = Color(0xff1573fe),
            textAlign = TextAlign.End,
            lineHeight = 1.43.em,
            style = TextStyle(
                fontSize = 14.sp,
                letterSpacing = 0.25.sp),
            modifier = Modifier
                .fillMaxSize()
                .offset( y = 6.dp))
    }
}

@Composable
fun Component14(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 317.dp)
            .requiredHeight(height = 33.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.shield),
            contentDescription = "line/media/notification-3-line")
        Text(
            text = "ความปลอดภัย",
            color = Color.Black,
            lineHeight = 1.33.em,
            style = TextStyle(
                fontSize = 15.sp,
                letterSpacing = 0.25.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 45.dp,
                    y = 5.dp)
                .fillMaxHeight()
                .requiredWidth(width = 240.dp))

    }
}

@Composable
fun Component15(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 317.dp)
            .requiredHeight(height = 33.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.contrast),
            contentDescription = "line/media/notification-3-line")
        Text(
            text = "ธีม",
            color = Color.Black,
            lineHeight = 1.33.em,
            style = TextStyle(
                fontSize = 15.sp,
                letterSpacing = 0.25.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 45.dp,
                    y = 5.dp)
                .fillMaxHeight()
                .requiredWidth(width = 240.dp))
        Text(
            text = "สว่าง",
            color = Color(0xff1573fe),
            textAlign = TextAlign.End,
            lineHeight = 1.43.em,
            style = TextStyle(
                fontSize = 14.sp,
                letterSpacing = 0.25.sp),
            modifier = Modifier
                .fillMaxSize()
                .offset(y = 6.dp))
    }
}

@Composable
fun Component16(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 317.dp)
            .requiredHeight(height = 33.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.help),
            contentDescription = "line/media/notification-3-line")
        Text(
            text = "การช่วยเหลือและสนับสนุน",
            color = Color.Black,
            lineHeight = 1.33.em,
            style = TextStyle(
                fontSize = 15.sp,
                letterSpacing = 0.25.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 45.dp,
                    y = 5.dp)
                .fillMaxHeight()
                .requiredWidth(width = 240.dp))

    }
}

@Composable
fun Component17(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 317.dp)
            .requiredHeight(height = 33.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.conversation),
            contentDescription = "line/media/notification-3-line")
        Text(
            text = "ติดต่อเรา",
            color = Color.Black,
            lineHeight = 1.33.em,
            style = TextStyle(
                fontSize = 15.sp,
                letterSpacing = 0.25.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 45.dp,
                    y = 5.dp)
                .fillMaxHeight()
                .requiredWidth(width = 240.dp))

    }
}

@Composable
fun Component18(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .requiredWidth(width = 317.dp)
            .requiredHeight(height = 33.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.unlock),
            contentDescription = "line/media/notification-3-line")
        Text(
            text = "ความเป็นส่วนตัว",
            color = Color.Black,
            lineHeight = 1.33.em,
            style = TextStyle(
                fontSize = 15.sp,
                letterSpacing = 0.25.sp),
            modifier = Modifier
                .align(alignment = Alignment.TopStart)
                .offset(x = 45.dp,
                    y = 5.dp)
                .fillMaxHeight()
                .requiredWidth(width = 240.dp))

    }
}
