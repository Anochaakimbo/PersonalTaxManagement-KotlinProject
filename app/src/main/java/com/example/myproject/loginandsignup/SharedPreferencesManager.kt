package com.example.myproject.loginandsignup

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPreferencesManager(context: Context) {
    private val preferences : SharedPreferences =
        context.getSharedPreferences("MyPreferences",Context.MODE_PRIVATE)

    var isLoggedIn:Boolean
        get() = preferences.getBoolean(KEY_IS_LOGGED_IN,false)
        set(value) = preferences.edit().putBoolean(KEY_IS_LOGGED_IN,value).apply()

    var userId : Int?
        get() = preferences.getInt(KEY_USER_ID,0)
        set(value) = preferences.edit().putInt(KEY_USER_ID,value!!).apply()

    var userEmail : String?
        get() = preferences.getString(KEY_USER_EMAIL,null)
        set(value) = preferences.edit().putString(KEY_USER_EMAIL,value).apply()

    fun clearUserAll(){
        preferences.edit {clear()}
    }

    fun clearUserLogin(){
        preferences.edit{remove(KEY_IS_LOGGED_IN) }
    }



    companion object{
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
    }
}