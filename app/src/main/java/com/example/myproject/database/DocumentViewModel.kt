package com.example.myproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myproject.api.DocumentAPI
import com.example.myproject.api.DocumentItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DocumentViewModel : ViewModel() {
    private val _documents = MutableStateFlow<List<DocumentItem>>(emptyList())
    val documents: StateFlow<List<DocumentItem>> = _documents

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val api = DocumentAPI.create()

    init {
        fetchDocuments()
    }

    private fun fetchDocuments() {
        viewModelScope.launch {
            try {
                _documents.value = api.getDocuments()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
