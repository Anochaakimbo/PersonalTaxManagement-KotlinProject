package com.example.myproject.database


data class ResetPasswordRequest(
    val email: String,
    val newPassword: String
)
