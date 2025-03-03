package com.example.myproject.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.api.DocumentAPI
import com.example.myproject.database.Document
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DocumentViewModel : ViewModel() {
    private val _documents = MutableStateFlow<List<Document>>(emptyList())
    private val _selectedYear = MutableStateFlow<Int?>(null)

    // ฟังก์ชันสำหรับกรองเอกสารตามปีที่เลือก
    val documents: StateFlow<List<Document>> = combine(
        _documents,
        _selectedYear
    ) { documents, selectedYear ->
        if (selectedYear == null) {
            documents // ถ้าไม่ได้เลือกปีให้แสดงทั้งหมด
        } else {
            documents.filter { it.year == selectedYear } // กรองตามปีที่เลือก
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val api = DocumentAPI.create()

    // ฟังก์ชันสำหรับอัพเดตปีที่เลือกจาก Screen
    fun updateSelectedYear(year: Int) {
        _selectedYear.value = year
        Log.d("DocumentViewModel", "Selected Year Updated: $year")
        fetchDocuments(1, year) // รีเฟรชข้อมูลเมื่อปีเปลี่ยน
    }

    // ฟังก์ชันดึงข้อมูลเอกสาร
    fun fetchDocuments(userId: Int, year: Int? = null) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val yearString = year?.toString()
                Log.d("DocumentViewModel", "Fetching documents for userId: $userId, year: $year")

                _documents.value = if (yearString != null) {
                    api.getDocumentsByUserAndYear(userId, year)
                } else {
                    emptyList()
                }
                Log.d("DocumentViewModel", "Fetched documents: ${_documents.value}")

                _errorMessage.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "ไม่สามารถโหลดเอกสารได้: ${e.message}"
                Log.e("DocumentViewModel", "Error fetching documents: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }


    // ฟังก์ชันอัปโหลดไฟล์
    fun uploadFile(context: Context, userId: Int, uri: Uri, year: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val file = uriToFile(context, uri)
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile)

                // สร้าง RequestBody สำหรับปี
                val yearBody = year.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                // เรียก API เพื่ออัปโหลดไฟล์
                api.uploadDocument(userId, filePart, yearBody)

                // รีเฟรชรายการเอกสารหลังจากอัปโหลด
                fetchDocuments(userId, _selectedYear.value)

            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "อัปโหลดไฟล์ล้มเหลว: ${e.message}"
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
                fetchDocuments(userId, _selectedYear.value) // รีเฟรชรายการหลังจากลบ
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "ลบไฟล์ล้มเหลว: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ฟังก์ชัน fetchDocumentsById ที่แก้ไข
    fun fetchDocumentsById(documentId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                Log.d("DocumentViewModel", "Fetching document with ID: $documentId")

                val document = api.getDocumentById(documentId)
                Log.d("DocumentViewModel", "Fetched document: $document")

                // อัพเดท documents state เพื่อให้ UI แสดงผล
                _documents.value = listOf(document)
                _errorMessage.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("DocumentViewModel", "Error fetching document: ${e.message}")
                _errorMessage.value = "ไม่สามารถโหลดเอกสารได้: ${e.message}"
                // เพื่อเป็นการป้องกัน ไม่ล้างข้อมูลเดิมถ้าเกิด error
                if (_documents.value.isEmpty()) {
                    _documents.value = emptyList()
                }
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun fetchDocumentsByUserAndYear(userId: Int, year: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _documents.value = api.getDocumentsByUserAndYear(userId, year)
                _errorMessage.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "ไม่สามารถโหลดเอกสารได้: ${e.message}"
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



