package com.example.myproject.mainscreen




import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myproject.R // ตรวจสอบให้ตรงกับ package จริง


//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            TaxSavingScreen()
//        }
//    }
//}

//@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun TaxSavingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAFBF1))
            .padding(16.dp)

    ) {
        HeaderSection()
        Spacer(modifier = Modifier.height(16.dp))
        TaxSavingProducts()
        Spacer(modifier = Modifier.height(16.dp))
        RecommendationsSection()

    }
}

@Composable
fun HeaderSection() {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3DDC84)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "แนะนำลดหย่อนภาษี",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = "เลือกทางถูกต้อง ด้วยตัวเลือกเหล่านี้",
                fontSize = 14.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun TaxSavingProducts() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = "ผลิตภัณฑ์ลดหย่อนภาษี",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            TaxProductItem(R.drawable.ic_savings, "ประกันชีวิต\nออมทรัพย์")
            TaxProductItem(R.drawable.ic_pension, "ประกันบำนาญ")
            TaxProductItem(R.drawable.ic_health, "ประกันสุขภาพ")
            TaxProductItem(R.drawable.ic_rmf, "กองทุน RMF")
            TaxProductItem(R.drawable.ic_ssf, "กองทุน SSF")
        }
    }
}

@Composable
fun TaxProductItem(icon: Int, name: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = name,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = name, fontSize = 12.sp)
    }
}

@Composable
fun RecommendationsSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(text = "แนะนำสำหรับคุณ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        RecommendationItem(R.drawable.ic_insurance, "บีแอลเอ สมาทร์เซฟวิ่ง 10/1", "ผลตอบแทนเฉลี่ย", "2.00% ต่อปี")
        RecommendationItem(R.drawable.ic_fund_rmf, "K-ESSGI-ThaiESG", "ผลตอบแทนเฉลี่ย", "2.38% ต่อปี")
    }
}

@Composable
fun RecommendationItem(icon: Int, title: String, subtitle: String, rate: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F2F2)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = title,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
                Text(text = rate, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}










//@Composable
//fun MyBottomBar(navController: NavHostController, contextForToast: Context) {
//    val navigationItems = listOf(
//        Screen.Home,
//        Screen.Search,
//        Screen.TaxAdd,
//        Screen.Notification,
//        Screen.Profile
//    )
//    var selectedScreen by remember { mutableIntStateOf(0) }
//    var showBottomSheet by remember { mutableStateOf(false) }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//        ) { }
//        NavigationBar(
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
//                .height(100.dp)
//                .clip(
//                    RoundedCornerShape(
//                        topStart = 50.dp,
//                        topEnd = 50.dp,
//                        bottomStart = 0.dp,
//                        bottomEnd = 0.dp
//                    )
//                ),
//            containerColor = Color(0xFFDFF7E2)
//        ) {
//            navigationItems.forEachIndexed { index, screen ->
//                NavigationBarItem(
//                    icon = {
//                        Icon(
//                            painter = painterResource(id = screen.icon),
//                            contentDescription = null,
//                            modifier = Modifier.size(26.dp)
//                        )
//                    },
//                    label = { Text(text = screen.name) },
//                    selected = (selectedScreen == index),
//                    onClick = {
//                        if (screen == Screen.TaxAdd) {
//                            showBottomSheet = true
//                        } else {
//                            selectedScreen = index
//                            navController.navigate(screen.route)
//                        }
//                    },
//                    colors = NavigationBarItemDefaults.colors(
//                        selectedIconColor = Color(0xFF4CAF50),
//                        unselectedIconColor = Color.Gray,
//                        selectedTextColor = Color(0xFF4CAF50),
//                        unselectedTextColor = Color.Gray,
//                        indicatorColor = Color(0xFFE8F5E9)
//                    )
//                )
//            }
//        }
//    }
//
//    if (showBottomSheet) {
//        TaxButtomSheet(onDismiss = { showBottomSheet = false })
//    }
//}


