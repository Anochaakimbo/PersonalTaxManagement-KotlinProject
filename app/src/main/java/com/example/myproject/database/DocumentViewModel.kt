package com.example.myproject.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.api.DocumentAPI
import com.example.myproject.api.DocumentItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

class DocumentViewModel : ViewModel() {
    private val _documents = MutableStateFlow<List<DocumentItem>>(emptyList())
    val documents: StateFlow<List<DocumentItem>> = _documents

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val api = DocumentAPI.create()

    init {
        fetchDocuments(1)
    }

    fun fetchDocuments(userId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _documents.value = api.getDocuments(userId)
                _errorMessage.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "ไม่สามารถโหลดเอกสารได้"
            } finally {
                _isLoading.value = false
            }
        }
    }

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

    fun formatThaiDate(isoDate: String?): String {
        if (isoDate.isNullOrEmpty()) return "ไม่มีข้อมูลวันที่"

        return try {
            Log.d("DateFormat", "Raw Date: $isoDate") // ✅ Debug

            val formatter = DateTimeFormatter.ISO_DATE_TIME
            val localDateTime = LocalDateTime.parse(isoDate, formatter) // แปลงวันที่
            val localDate = localDateTime.atZone(ZoneId.systemDefault()).toLocalDate()

            val thaiMonths = arrayOf(
                "มกราคม", "กุมภาพันธ์", "มีนาคม", "เมษายน", "พฤษภาคม", "มิถุนายน",
                "กรกฎาคม", "สิงหาคม", "กันยายน", "ตุลาคม", "พฤศจิกายน", "ธันวาคม"
            )

            val day = localDate.dayOfMonth
            val month = thaiMonths[localDate.monthValue - 1] // แปลงเดือนเป็นไทย
            val year = localDate.year + 543 // แปลง ค.ศ. ➝ พ.ศ.

            val formattedDate = "$day $month $year"
            Log.d("DateFormat", "Formatted Date: $formattedDate") // ✅ Debug

            formattedDate
        } catch (e: Exception) {
            Log.e("DateFormat", "Error parsing date: ${e.message}")
            "แปลงวันที่ไม่ได้"
        }
    }




    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val file = File(context.cacheDir, "upload_file")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file
    }
}