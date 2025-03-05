import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.myproject.R
import com.example.myproject.api.IncomeAPI
import com.example.myproject.api.TaxDeductionAPI
import com.example.myproject.database.AllTaxDeductionClass
import com.example.myproject.database.AllincomeUserClass
import com.example.myproject.loginandsignup.SharedPreferencesManager
import com.example.myproject.navigation.Screen
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


val incomeTypeMap = mapOf(
    "1" to "เงินเดือน",
    "2" to "งานฟรีแลนซ์",
    "3" to "ขายของออนไลน์",
    "4" to "ลงทุนหุ้น",
    "5" to "กำไรจากคริปโต",
    "6" to "ดอกเบี้ยเงินฝาก"
)


// สร้าง Map ของประเภทการหักภาษี
val taxDeductionTypeMap = mapOf(
    "1" to "ประกันสังคม",
    "2" to "Easy E-Receipt",
    "3" to "ดอกเบี้ยที่บ้าน",
    "4" to "ประกันชีวิตทั่วไป",
    "5" to "บริจาคทั่วไป",
    "6" to "Thai ESG",

    )

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val sharedPreferencesManager = remember { SharedPreferencesManager(context) }
    var selectedYear by remember { mutableStateOf(sharedPreferencesManager.selectedYear) }
    var expanded by remember { mutableStateOf(false) }

    // ดึง userId จาก SharedPreferencesManager
    val userId = sharedPreferencesManager.userId ?: 0

    // สร้าง state สำหรับเก็บข้อมูลรายได้
    var incomeList by remember { mutableStateOf<List<AllincomeUserClass>>(emptyList()) }
    var taxDeductionList by remember { mutableStateOf<List<AllTaxDeductionClass>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // สร้าง exception handler สำหรับจัดการข้อผิดพลาด
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("HomeScreen", "เกิดข้อผิดพลาด: ${throwable.message}", throwable)
        errorMessage = "เกิดข้อผิดพลาด: ${throwable.message}"
        isLoading = false
    }

    // ดึงข้อมูลรายได้เมื่อหน้าจอถูกโหลดหรือเมื่อปีเปลี่ยน
    LaunchedEffect(selectedYear, userId) {
        isLoading = true
        errorMessage = null

        try {
            //Income API
            val incomeApi = try {
                IncomeAPI.create()
            } catch (e: Exception) {
                Log.e("HomeScreen", "ไม่สามารถสร้าง Income API ได้: ${e.message}", e)
                errorMessage = "ไม่สามารถเชื่อมต่อกับเซิร์ฟเวอร์ได้"
                isLoading = false
                return@LaunchedEffect
            }

            //TaxDeduction API
            val taxDeductionApi = try {
                TaxDeductionAPI.create()
            } catch (e: Exception) {
                Log.e("HomeScreen", "ไม่สามารถสร้าง TaxDeduction API ได้: ${e.message}", e)
                errorMessage = "ไม่สามารถเชื่อมต่อกับเซิร์ฟเวอร์ได้"
                isLoading = false
                return@LaunchedEffect
            }

            // ดึงข้อมูล Income
            withContext(Dispatchers.IO + exceptionHandler) {
                fetchIncomeDataSafely(
                    incomeApi = incomeApi,
                    userId = userId ?: 0,
                    onSuccess = { data ->
                        incomeList = data.filter { it.user_id == userId }
                        // อย่าตั้ง isLoading = false ที่นี่ เพราะเรายังต้องรอข้อมูล TaxDeduction
                    },
                    onError = { message ->
                        errorMessage = message
                        isLoading = false
                    }
                )

                // ดึงข้อมูล TaxDeduction
                fetchTaxDeductionDataSafely(
                    taxDeductionApi = taxDeductionApi,
                    userId = userId ?: 0,
                    onSuccess = { data ->
                        taxDeductionList = data.filter { it.user_id == userId }
                        isLoading = false  // ตั้งเป็น false หลังจากดึงข้อมูลทั้งหมดแล้ว
                    },
                    onError = { message ->
                        errorMessage = message
                        isLoading = false
                    }
                )
            }
        } catch (e: Exception) {
            Log.e("HomeScreen", "เกิดข้อผิดพลาดในการดึงข้อมูล: ${e.message}", e)
            errorMessage = "เกิดข้อผิดพลาดในการดึงข้อมูล: ${e.message}"
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            com.example.myproject.components.TopAppBar(
                navController = navController,
                modifier = Modifier.zIndex(1f)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(bottom = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                // กล่องสำหรับเลือกปี
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, top = 8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
                            .clickable { expanded = !expanded }
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$selectedYear",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Dropdown Year"
                        )
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .width(120.dp)
                            .background(Color.White),
                        offset = DpOffset(x = (-16).dp, y = 0.dp)
                    ) {
                        listOf(2564,2565,2566,2567, 2568).forEach { year ->
                            DropdownMenuItem(
                                text = { Text(text = year.toString()) },
                                onClick = {
                                    selectedYear = year
                                    sharedPreferencesManager.selectedYear = year // บันทึกค่า
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                val totalIncome = incomeList.filter { it.year == selectedYear.toString() && it.user_id == userId }.sumOf { it.incomebalance }
                val totalDeduction = taxDeductionList.filter { it.year == selectedYear.toString() && it.user_id == userId }.sumOf { it.taxdeductiontype_balance }
                val taxAmount = calculateTax(totalIncome, totalDeduction)


                // กล่อง "เริ่มคำนวณภาษี"
                Box(
                    modifier = Modifier
                        .size(width = 300.dp, height = 300.dp)
                        .background(Color(0xFF00695C), shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.money_transfer_test),
                            contentDescription = "Tax Icon",
                            modifier = Modifier.size(100.dp).padding(bottom = 8.dp)
                        )
                        Text(
                            text = "ภาษีที่คุณต้องจ่าย",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "(รวมกับค่าลดหย่อนส่วนตัว)",
                            color = Color.White,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "${String.format("%,.2f", taxAmount)} บาท",
                            color = Color.White,
                            fontSize = 20.sp,
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                navController.navigate(Screen.AddIncome.route)
                                try {
                                    // เพิ่มโค้ดที่จะทำงานเมื่อกดปุ่ม START
                                } catch (e: Exception) {
                                    Toast.makeText(context, "เกิดข้อผิดพลาด: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            Text(
                                text = "START",
                                color = Color(0xFF00695C),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                // แสดงข้อมูลรายได้ทั้งหมด - แก้ไขให้กรองตาม userId และ selectedYear
                IncomeInfoCard(
                    incomeList = incomeList.filter { it.year == selectedYear.toString() && it.user_id == userId },
                    currentYear = selectedYear.toString(),
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    navController = navController,
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            item {
                TaxDeductionInfoCard(
                    taxDeductionList = taxDeductionList.filter { it.year == selectedYear.toString() && it.user_id == userId },
                    currentYear = selectedYear.toString(),
                    isLoading = isLoading,
                    errorMessage = errorMessage,
                    navController = navController
                )
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

private fun calculateTax(totalIncome: Int, totalDeduction: Int): Double {
    // 1. คำนวณค่าใช้จ่ายเหมา (ประมาณการ 50% แต่ไม่เกิน 100,000 บาท)
    val standardExpense = minOf(totalIncome * 0.5, 100000.0).toInt()
    // 2. หักค่าใช้จ่ายเหมา
    val incomeAfterExpense = totalIncome - standardExpense
    // 3. หักค่าลดหย่อนทั้งหมด (ค่าลดหย่อนที่ผู้ใช้ป้อน + ค่าลดหย่อนส่วนตัว)
    val personalDeduction = 60000 // ค่าลดหย่อนส่วนตัว
    val totalAllDeductions = totalDeduction + personalDeduction
    // 4. คำนวณเงินได้สุทธิ
    val netTaxableIncome = maxOf(0, incomeAfterExpense - totalAllDeductions)
    // 5. คำนวณภาษีแบบขั้นบันได
    var tax = 0.0
    // ช่วงที่ 0%
    if (netTaxableIncome <= 150000) {
        return 0.0
    }
    // ช่วงที่ 5%
    if (netTaxableIncome > 150000) {
        val taxableAmount = minOf(netTaxableIncome, 300000) - 150000
        tax += taxableAmount * 0.05
    }
    // ช่วงที่ 10%
    if (netTaxableIncome > 300000) {
        val taxableAmount = minOf(netTaxableIncome, 500000) - 300000
        tax += taxableAmount * 0.10
    }
    // ช่วงที่ 15%
    if (netTaxableIncome > 500000) {
        val taxableAmount = minOf(netTaxableIncome, 750000) - 500000
        tax += taxableAmount * 0.15
    }

    // ช่วงที่ 20%
    if (netTaxableIncome > 750000) {
        val taxableAmount = minOf(netTaxableIncome, 1000000) - 750000
        tax += taxableAmount * 0.20
    }

    // ช่วงที่ 25%
    if (netTaxableIncome > 1000000) {
        val taxableAmount = minOf(netTaxableIncome, 2000000) - 1000000
        tax += taxableAmount * 0.25
    }

    // ช่วงที่ 30%
    if (netTaxableIncome > 2000000) {
        val taxableAmount = minOf(netTaxableIncome, 5000000) - 2000000
        tax += taxableAmount * 0.30
    }

    // ช่วง 35%
    if (netTaxableIncome > 5000000) {
        val taxableAmount = netTaxableIncome - 5000000
        tax += taxableAmount * 0.35
    }
    return tax
}


@Composable
fun IncomeInfoCard(
    navController: NavHostController,
    incomeList: List<AllincomeUserClass>,
    currentYear: String,
    isLoading: Boolean,
    errorMessage: String?
) {
    val context = LocalContext.current
    // ใช้ incomeList เป็นค่าเริ่มต้นของ updatedIncomeList
    var updatedIncomeList by remember(incomeList) { mutableStateOf(incomeList) }
    var isDeleting by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE)),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "รายได้ทั้งหมด (ปี $currentYear)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            when {
                isLoading -> Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "กำลังโหลดข้อมูล...")
                }
                errorMessage != null -> Text(text = errorMessage, color = Color.Red, fontSize = 14.sp)
                else -> {
                    val totalIncome = updatedIncomeList.sumOf { it.incomebalance }
                    Text(text = "${String.format("%,d", totalIncome)} บาท", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(text = "จำนวน ${updatedIncomeList.size} รายการ", fontSize = 14.sp, color = Color.Gray)

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        updatedIncomeList.forEach { income ->
                            val incomeTypeName = incomeTypeMap[income.incometype_id] ?: "ไม่ทราบประเภท"
                            val myFont = FontFamily(Font(R.font.ibmplexsansthai_regular))
                            var deleteDialog by remember { mutableStateOf(false) }
                            var deleteIncomeId by remember { mutableStateOf(0) }

                            Card(
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    // ข้อมูลรายได้
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = incomeTypeName,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF00695C)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "${String.format("%,d", income.incomebalance)} บาท",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        // ปุ่มแก้ไข ยังไม่ได้ทำฟังก์ชัน update เบย
                                        OutlinedButton(
                                            onClick = {
                                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                                    "incomeData",
                                                    mapOf(
                                                        "id" to income.id,
                                                        "incometype_id" to income.incometype_id,
                                                        "incometype_name" to (incomeTypeMap[income.incometype_id] ?: "ไม่ทราบประเภท"),
                                                        "year" to income.year,
                                                        "incomebalance" to income.incomebalance,
                                                        "user_id" to income.user_id
                                                    )
                                                )
                                                navController.navigate(Screen.EditIncome.route)
                                            },
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = Color(0xFF00695C)
                                            ),
                                            border = BorderStroke(1.dp, Color(0xFF00695C)),
                                            modifier = Modifier.padding(end = 8.dp),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                        ) {
                                            Text("แก้ไข",
                                                fontSize = 12.sp,
                                                style = MaterialTheme.typography.headlineMedium.copy(fontFamily = myFont))
                                        }

                                        // ปุ่มลบ
                                        Button(
                                            onClick = {
                                                deleteDialog = true
                                                deleteIncomeId = income.id
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFE57373),
                                                contentColor = Color.White
                                            ),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                        ) {
                                            Text(
                                                text ="ลบ",
                                                style = MaterialTheme.typography.headlineMedium.copy(fontFamily = myFont),
                                                fontSize = 12.sp,
                                            )
                                        }

                                        if (deleteDialog) {
                                            AlertDialog(
                                                onDismissRequest = { deleteDialog = false },
                                                title = { Text("ยืนยันการลบ", style = MaterialTheme.typography.headlineMedium.copy(fontFamily = myFont)) },
                                                text = { Text("คุณต้องการลบรายการรายได้นี้ใช่หรือไม่?", style = MaterialTheme.typography.bodyMedium.copy(fontFamily = myFont)) },
                                                confirmButton = {
                                                    Button(
                                                        onClick = {
                                                            isDeleting = true

                                                            try {
                                                                val incomeApi = IncomeAPI.create()
                                                                incomeApi.deleteIncome(deleteIncomeId).enqueue(object : Callback<AllincomeUserClass> {
                                                                    override fun onResponse(
                                                                        call: Call<AllincomeUserClass>,
                                                                        response: Response<AllincomeUserClass>
                                                                    ) {
                                                                        if (response.isSuccessful) {
                                                                            updatedIncomeList = updatedIncomeList.filter { it.id !== deleteIncomeId }
                                                                            Toast.makeText(context, "ลบรายการสำเร็จ", Toast.LENGTH_SHORT).show()
                                                                        } else {
                                                                            Toast.makeText(context, "ไม่สามารถลบรายการได้: รหัสข้อผิดพลาด ${response.code()}", Toast.LENGTH_SHORT).show()
                                                                            Log.e("IncomeInfoCard", "ไม่สามารถลบรายการได้: ${response.code()}")
                                                                        }
                                                                        isDeleting = false
                                                                    }

                                                                    override fun onFailure(call: Call<AllincomeUserClass>, t: Throwable) {
                                                                        Toast.makeText(context, "เกิดข้อผิดพลาด: ${t.message}", Toast.LENGTH_SHORT).show()
                                                                        Log.e("IncomeInfoCard", "เกิดข้อผิดพลาดในการลบ: ${t.message}", t)
                                                                        isDeleting = false
                                                                    }
                                                                })
                                                            } catch (e: Exception) {
                                                                Toast.makeText(context, "เกิดข้อผิดพลาด: ${e.message}", Toast.LENGTH_SHORT).show()
                                                                Log.e("IncomeInfoCard", "เกิดข้อผิดพลาดในการสร้าง API: ${e.message}", e)
                                                                isDeleting = false
                                                            }

                                                            deleteDialog = false
                                                        },
                                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)),
                                                    ) {
                                                        Text("ยืนยัน", style = MaterialTheme.typography.labelMedium.copy(fontFamily = myFont))
                                                    }
                                                },
                                                dismissButton = {
                                                    OutlinedButton(
                                                        onClick = { deleteDialog = false },
                                                        border = BorderStroke(1.dp, Color(0xFF00695C))
                                                    ) {
                                                        Text("ยกเลิก", style = MaterialTheme.typography.labelMedium.copy(fontFamily = myFont))
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (isDeleting) {
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text("กำลังดำเนินการ") },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("กำลังลบรายการ...")
                        }
                    },
                    confirmButton = { }
                )
            }
        }
    }
}

@Composable
fun TaxDeductionInfoCard(
    navController: NavHostController,
    taxDeductionList: List<AllTaxDeductionClass>,
    currentYear: String,
    isLoading: Boolean,
    errorMessage: String?
) {
    val context = LocalContext.current
    val myFont = FontFamily(Font(R.font.ibmplexsansthai_regular))
    var updatedTaxDeductionList by remember(taxDeductionList) { mutableStateOf(taxDeductionList) }
    var isDeleting by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE1F5FE)),
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "การลดหย่อนภาษีทั้งหมด (ปี $currentYear)", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            when {
                isLoading -> Row(verticalAlignment = Alignment.CenterVertically) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "กำลังโหลดข้อมูล...")
                }
                errorMessage != null -> Text(text = errorMessage, color = Color.Red, fontSize = 14.sp)
                else -> {
                    val totalTaxDeduction = updatedTaxDeductionList.sumOf { it.taxdeductiontype_balance }
                    Text(text = "$totalTaxDeduction บาท", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    Text(text = "จำนวน ${updatedTaxDeductionList.size} รายการ", fontSize = 14.sp, color = Color.Gray)

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        updatedTaxDeductionList.forEach { taxDeduction ->
                            val taxDeductionTypeName = taxDeductionTypeMap[taxDeduction.taxdeductiontype_id] ?: "ไม่ทราบประเภท"
                            var deleteDialog by remember { mutableStateOf(false) }
                            var deleteTaxId by remember { mutableStateOf(0) }

                            Card(
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    // ข้อมูลการหักภาษี
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Text(
                                            text = taxDeductionTypeName,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = Color(0xFF00695C)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = "${taxDeduction.taxdeductiontype_balance} บาท",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    // ปุ่ม แก้ไข และ ลบ
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        // ปุ่มแก้ไข  ยังไม่ได้ทำฟังก์ชัน update เบย
                                        OutlinedButton(
                                            onClick = {
                                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                                    "taxData",
                                                    mapOf(
                                                        "id" to taxDeduction.id,
                                                        "taxdeductiontype_id" to taxDeduction.taxdeductiontype_id,
                                                        "taxdeductiontype_name" to (taxDeductionTypeMap[taxDeduction.taxdeductiontype_id] ?: "ไม่ทราบประเภท"),
                                                        "year" to taxDeduction.year,
                                                        "taxdeductionbalance" to taxDeduction.taxdeductiontype_balance,
                                                        "user_id" to taxDeduction.user_id
                                                    )
                                                )
                                                navController.navigate(Screen.EditTaxDeduc.route)
                                            },
                                            colors = ButtonDefaults.outlinedButtonColors(
                                                contentColor = Color(0xFF00695C)
                                            ),
                                            border = BorderStroke(1.dp, Color(0xFF00695C)),
                                            modifier = Modifier.padding(end = 8.dp),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                        ) {
                                            Text("แก้ไข",
                                                fontSize = 12.sp,
                                                style = MaterialTheme.typography.headlineMedium.copy(fontFamily = myFont))
                                        }

                                        // ปุ่มลบ
                                        Button(
                                            onClick = {
                                                deleteDialog = true
                                                deleteTaxId = taxDeduction.id
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFE57373),
                                                contentColor = Color.White
                                            ),
                                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                        ) {
                                            Text(
                                                text ="ลบ",
                                                style = MaterialTheme.typography.headlineMedium.copy(fontFamily = myFont),
                                                fontSize = 12.sp,
                                            )
                                        }

                                        // Dialog ยืนยันการลบ
                                        if (deleteDialog) {
                                            AlertDialog(
                                                onDismissRequest = { deleteDialog = false },
                                                title = { Text("ยืนยันการลบ", style = MaterialTheme.typography.headlineMedium.copy(fontFamily = myFont)) },
                                                text = { Text("คุณต้องการลบรายการหักภาษีนี้ใช่หรือไม่?", style = MaterialTheme.typography.bodyMedium.copy(fontFamily = myFont)) },
                                                confirmButton = {
                                                    Button(
                                                        onClick = {
                                                            // เริ่มลบข้อมูล
                                                            isDeleting = true

                                                            // เรียกใช้ API สำหรับการลบข้อมูล
                                                            try {
                                                                val taxDeductionApi = TaxDeductionAPI.create()
                                                                taxDeductionApi.deleteTax(deleteTaxId).enqueue(object : Callback<AllTaxDeductionClass> {
                                                                    override fun onResponse(
                                                                        call: Call<AllTaxDeductionClass>,
                                                                        response: Response<AllTaxDeductionClass>
                                                                    ) {
                                                                        if (response.isSuccessful) {
                                                                            // อัปเดตรายการหลังจากลบสำเร็จ
                                                                            updatedTaxDeductionList = updatedTaxDeductionList.filter { it.id != deleteTaxId }
                                                                            Toast.makeText(context, "ลบรายการสำเร็จ", Toast.LENGTH_SHORT).show()
                                                                        } else {
                                                                            Toast.makeText(context, "ไม่สามารถลบรายการได้: รหัสข้อผิดพลาด ${response.code()}", Toast.LENGTH_SHORT).show()
                                                                            Log.e("TaxDeductionInfoCard", "ไม่สามารถลบรายการได้: ${response.code()}")
                                                                        }
                                                                        isDeleting = false
                                                                    }

                                                                    override fun onFailure(call: Call<AllTaxDeductionClass>, t: Throwable) {
                                                                        Toast.makeText(context, "เกิดข้อผิดพลาด: ${t.message}", Toast.LENGTH_SHORT).show()
                                                                        Log.e("TaxDeductionInfoCard", "เกิดข้อผิดพลาดในการลบ: ${t.message}", t)
                                                                        isDeleting = false
                                                                    }
                                                                })
                                                            } catch (e: Exception) {
                                                                Toast.makeText(context, "เกิดข้อผิดพลาด: ${e.message}", Toast.LENGTH_SHORT).show()
                                                                Log.e("TaxDeductionInfoCard", "เกิดข้อผิดพลาดในการสร้าง API: ${e.message}", e)
                                                                isDeleting = false
                                                            }

                                                            deleteDialog = false
                                                        },
                                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE57373)),
                                                    ) {
                                                        Text("ยืนยัน", style = MaterialTheme.typography.labelMedium.copy(fontFamily = myFont))
                                                    }
                                                },
                                                dismissButton = {
                                                    OutlinedButton(
                                                        onClick = { deleteDialog = false },
                                                        border = BorderStroke(1.dp, Color(0xFF00695C))
                                                    ) {
                                                        Text("ยกเลิก", style = MaterialTheme.typography.labelMedium.copy(fontFamily = myFont))
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (isDeleting) {
                AlertDialog(
                    onDismissRequest = { },
                    title = { Text("กำลังดำเนินการ") },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("กำลังลบรายการ...")
                        }
                    },
                    confirmButton = { }
                )
            }
        }
    }
}

private fun fetchIncomeDataSafely(
    incomeApi: IncomeAPI,
    userId: Int, // เพิ่มพารามิเตอร์ userId
    onSuccess: (List<AllincomeUserClass>) -> Unit,
    onError: (String) -> Unit
) {
    try {
        val call = incomeApi.getAllIncome()

        call.enqueue(object : Callback<List<AllincomeUserClass>> {
            override fun onResponse(
                call: Call<List<AllincomeUserClass>>,
                response: Response<List<AllincomeUserClass>>
            ) {
                try {
                    if (response.isSuccessful) {
                        val allData = response.body() ?: emptyList()
                        // กรองข้อมูลเฉพาะของ user_id ที่ตรงกับ userId
                        val userSpecificData = allData.filter { it.user_id == userId }
                        Log.d("HomeScreen", "ได้รับข้อมูลรายได้ของ user $userId จำนวน ${userSpecificData.size} รายการ จากทั้งหมด ${allData.size} รายการ")
                        onSuccess(userSpecificData)
                    } else {
                        val errorMsg = "รหัสข้อผิดพลาด: ${response.code()}"
                        Log.e("HomeScreen", errorMsg)
                        onError(errorMsg)
                    }
                } catch (e: Exception) {
                    Log.e("HomeScreen", "ข้อผิดพลาดในการประมวลผลข้อมูล: ${e.message}", e)
                    onError("ข้อผิดพลาดในการประมวลผลข้อมูล: ${e.message}")
                }
            }

            override fun onFailure(call: Call<List<AllincomeUserClass>>, t: Throwable) {
                val errorMsg = "ไม่สามารถเชื่อมต่อกับเซิร์ฟเวอร์ได้: ${t.message}"
                Log.e("HomeScreen", errorMsg, t)
                onError(errorMsg)
            }
        })
    } catch (e: Exception) {
        val errorMsg = "เกิดข้อผิดพลาดในการเรียก API: ${e.message}"
        Log.e("HomeScreen", errorMsg, e)
        onError(errorMsg)
    }
}
private fun fetchTaxDeductionDataSafely(
    taxDeductionApi: TaxDeductionAPI,
    userId: Int,
    onSuccess: (List<AllTaxDeductionClass>) -> Unit,
    onError: (String) -> Unit
) {
    try {
        val call = taxDeductionApi.getAllTaxDeduction()

        call.enqueue(object : Callback<List<AllTaxDeductionClass>> {
            override fun onResponse(
                call: Call<List<AllTaxDeductionClass>>,
                response: Response<List<AllTaxDeductionClass>>
            ) {
                try {
                    if (response.isSuccessful) {
                        val allData = response.body() ?: emptyList()
                        val userSpecificData = allData.filter { it.user_id == userId }
                        Log.d("HomeScreen", "ได้รับข้อมูลการหักภาษีของ user $userId จำนวน ${userSpecificData.size} รายการ")
                        onSuccess(userSpecificData)
                    } else {
                        val errorMsg = "รหัสข้อผิดพลาด: ${response.code()}"
                        Log.e("HomeScreen", errorMsg)
                        onError(errorMsg)
                    }
                } catch (e: Exception) {
                    Log.e("HomeScreen", "ข้อผิดพลาดในการประมวลผลข้อมูล: ${e.message}", e)
                    onError("ข้อผิดพลาดในการประมวลผลข้อมูล: ${e.message}")
                }
            }

            override fun onFailure(call: Call<List<AllTaxDeductionClass>>, t: Throwable) {
                val errorMsg = "ไม่สามารถเชื่อมต่อกับเซิร์ฟเวอร์ได้: ${t.message}"
                Log.e("HomeScreen", errorMsg, t)
                onError(errorMsg)
            }
        })
    } catch (e: Exception) {
        val errorMsg = "เกิดข้อผิดพลาดในการเรียก API: ${e.message}"
        Log.e("HomeScreen", errorMsg, e)
        onError(errorMsg)
    }
}
