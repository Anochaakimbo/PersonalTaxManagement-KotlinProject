package com.example.myproject.navigation

import androidx.annotation.DrawableRes
import com.example.myproject.R

sealed class Screen(val route: String, val name: String, @DrawableRes val icon: Int) {
    data object Home : Screen(route = "home_screen", name = "", icon = R.drawable.home)
    data object Search : Screen(route = "search_screen", name = "", icon = R.drawable.analyzer)
    data object TaxAdd : Screen(route = "taxadd_screen", name = "", icon = R.drawable.add)
    data object Notification : Screen(route = "notification_screen", name = "", icon = R.drawable.bell)
    data object Profile : Screen(route = "profile_screen", name = "", icon = R.drawable.menu)
    data object TaxDeduction : Screen(route = "taxdeduction_screen", name = "", icon = R.drawable.menu)
    data object Login : Screen(route = "login_screen", name = "Login",icon =R.drawable.menu)
    data object AddIncome : Screen(route = "addincome_screen", name = "", icon = R.drawable.menu)
    data object Register : Screen(route = "register_screen", name = "Register",icon = R.drawable.menu)
    data object Forgetpassword : Screen(route = "forgetpassword_screen", name = "Forgetpassword",icon = R.drawable.menu)
    data object EditProfileScreen : Screen(route = "editprofilescreen_screen", name = "EditProfile",icon = R.drawable.trending)
    data object SecureScreen : Screen(route = "securescreen_screen", name = "Secure",icon = R.drawable.trending)
    data object PrivacyScreen : Screen(route = "privacyscreen_screen", name = "Privacy",icon = R.drawable.trending)
    data object ContactScreen : Screen(route = "contactscreen_screen", name = "Contact",icon = R.drawable.trending)

}