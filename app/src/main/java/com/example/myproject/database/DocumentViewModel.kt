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
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

class DocumentViewModel : ViewModel() {
    private val _documents = MutableStateFlow<List<DocumentItem>>(emptyList())
    val documents: StateFlow<List<DocumentItem>> = _documents

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val api = DocumentAPI.create()

    // ✅ ดึงเอกสารอัตโนมัติเมื่อ ViewModel ถูกสร้าง (ใช้ user_id = 1 เป็นค่าเริ่มต้น)
    init {
        fetchDocuments(1)
    }

    // ✅ ดึงเอกสารของ user_id ที่กำหนด
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

    // ✅ อัปโหลดไฟล์ไปยังเซิร์ฟเวอร์
    fun uploadFile(context: Context, userId: Int, uri: Uri) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                Log.d("Upload", "เริ่มอัปโหลดไฟล์: userId=$userId, uri=$uri")

                val file = uriToFile(context, uri)
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
                val filePart = MultipartBody.Part.createFormData("file", file.name, requestFile) // ✅ ใช้ "file"

                Log.d("Upload", "กำลังส่ง API: userId=$userId, file=${file.name}")
                val response = api.uploadDocument(userId, filePart)

                Log.d("Upload", "อัปโหลดสำเร็จ: $response")
                fetchDocuments(userId) // รีเฟรชรายการเอกสาร
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.value = "อัปโหลดไฟล์ล้มเหลว"
                Log.e("Upload", "เกิดข้อผิดพลาด: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }


    // ✅ ลบไฟล์จากเซิร์ฟเวอร์
    fun deleteFile(userId: Int, fileId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                api.deleteFile(fileId)
                fetchDocuments(userId) // รีเฟรชรายการเอกสาร
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
                _documents.value = listOf(document) // เก็บไฟล์เดียวที่ดึงมา
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // ✅ แปลง Uri เป็นไฟล์เพื่ออัปโหลด
    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)!!
        val file = File(context.cacheDir, "upload_file")
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        return file
    }
}
