package com.example.myproject.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.api.DocumentAPI
import com.example.myproject.api.DocumentItem
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DocumentViewModel : ViewModel() {
    private val _documents = MutableStateFlow<List<DocumentItem>>(emptyList())

    // เพิ่ม StateFlow สำหรับเก็บปีที่เลือก
    private val _selectedYear = MutableStateFlow("ทั้งหมด")
    val selectedYear: StateFlow<String> = _selectedYear

    // ปรับ documents ให้แสดงผลตามปีที่เลือก
    val documents: StateFlow<List<DocumentItem>> = _selectedYear
        .flatMapLatest { year ->
            if (year == "ทั้งหมด") {
                _documents
            } else {
                flow {
                    emit(_documents.value.filter { doc ->
                        try {
                            val formatter = DateTimeFormatter.ISO_DATE_TIME
                            val localDateTime = LocalDateTime.parse(doc.uploaded_at, formatter)
                            val docYear = localDateTime.year.toString()
                            // แปลง ค.ศ. เป็น พ.ศ.
                            val thaiYear = (localDateTime.year + 543).toString()
                            thaiYear == year
                        } catch (e: Exception) {
                            Log.e("DocumentViewModel", "Error parsing date: ${doc.uploaded_at}", e)
                            false
                        }
                    })
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val api = DocumentAPI.create()

    init {
        fetchDocuments(1)
    }

    // ฟังก์ชันสำหรับอัพเดตปีที่เลือก
    fun updateSelectedYear(year: String) {
        _selectedYear.value = year
        fetchDocuments(1, year)  // ใช้ userId = 1 หรือค่าที่เหมาะสม
    }

    fun fetchDocuments(userId: Int, year: String? = null) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _documents.value = api.getDocuments(userId, if (year == "ทั้งหมด") null else year)
                _errorMessage.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "ไม่สามารถโหลดเอกสารได้"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ฟังก์ชันอัปโหลดไฟล์
    fun uploadFile(context: Context, userId: Int, uri: Uri) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val file = uriToFile(context, uri)
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

                api.uploadDocument(userId, filePart)
                fetchDocuments(userId)
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "อัปโหลดไฟล์ล้มเหลว"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ฟังก์ชันลบไฟล์
    fun deleteFile(userId: Int, fileId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                api.deleteFile(fileId)
                fetchDocuments(userId)
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "ลบไฟล์ล้มเหลว"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ฟังก์ชันดึงข้อมูลเอกสารตาม ID
    fun fetchDocumentsById(documentId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val document = api.getDocumentById(documentId)
                _documents.value = listOf(document)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ฟังก์ชันแปลงวันที่เป็นรูปแบบไทย
    fun formatThaiDate(isoDate: String?): String {
        if (isoDate.isNullOrEmpty()) return "ไม่มีข้อมูลวันที่"

        return try {
            Log.d("DateFormat", "Raw Date: $isoDate")

            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val localDateTime = LocalDateTime.parse(isoDate, formatter)
            val localDate = localDateTime.atZone(ZoneId.systemDefault()).toLocalDate()

            val thaiMonths = arrayOf(
                "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน",
                "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"
            )

            val day = localDate.dayOfMonth
            val month = thaiMonths[localDate.monthValue - 1]
            val year = localDate.year + 543 // แปลง ค.ศ. ➝ พ.ศ.

            val formattedDate = "$day $month $year"
            Log.d("DateFormat", "Formatted Date: $formattedDate")

            formattedDate
        } catch (e: Exception) {
            Log.e("DateFormat", "Error parsing date: ${e.message}")
            "แปลงวันที่ไม่ได้"
        }
    }

    // ฟังก์ชันแปลง URI เป็นไฟล์
    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val file = File(context.cacheDir, "upload_file")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file
    }
}