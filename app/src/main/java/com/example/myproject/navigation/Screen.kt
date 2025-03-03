package com.example.myproject.navigation

import androidx.annotation.DrawableRes
import com.example.myproject.R

sealed class Screen(val route: String, val name: String, @DrawableRes val icon: Int) {
    // 🔹 Main Screens
    data object Home : Screen(route = "home_screen", name = "", icon = R.drawable.home)
    data object Search : Screen(route = "search_screen", name = "", icon = R.drawable.analyzer)
    data object TaxAdd : Screen(route = "taxadd_screen", name = "", icon = R.drawable.add)
    data object TaxSaving : Screen(route = "taxsaving_screen", name = "Tax Saving", icon = R.drawable.menu)
    data object Notification : Screen(route = "notification_screen", name = "", icon = R.drawable.bell)
    data object Profile : Screen(route = "profile_screen", name = "", icon = R.drawable.menu)
    data object TaxDeduction : Screen(route = "taxdeduction_screen", name = "", icon = R.drawable.menu)
    data object AddIncome : Screen(route = "addincome_screen", name = "", icon = R.drawable.menu)

    // 🔹 Authentication Screens
    data object Login : Screen(route = "login_screen", name = "Login", icon = R.drawable.menu)
    data object Register : Screen(route = "register_screen", name = "Register", icon = R.drawable.menu)

    // 🔥 เพิ่ม ForgetPassword และ ResetPassword
    data object ForgetPassword : Screen(route = "forgetpassword_screen", name = "Forget Password", icon = R.drawable.menu)
    data object ResetPassword : Screen(route = "resetpassword_screen/{email}", name = "Reset Password", icon = R.drawable.menu) {
        fun withArgs(email: String): String {
            return "resetpassword_screen/$email"
        }
    }

    // 🔹 Profile Management
    data object EditProfileScreen : Screen(route = "editprofilescreen_screen", name = "Edit Profile", icon = R.drawable.trending)
    data object SecureScreen : Screen(route = "securescreen_screen", name = "Secure", icon = R.drawable.trending)
    data object PrivacyScreen : Screen(route = "privacyscreen_screen", name = "Privacy", icon = R.drawable.trending)
    data object ContactScreen : Screen(route = "contactscreen_screen", name = "Contact", icon = R.drawable.trending)

    // 🔹 Document Management
    data object ShowAllDocument : Screen(route = "showalldocument_screen", name = "", icon = R.drawable.receipt)
    data object SaveDocument : Screen(route = "savedocument_screen", name = "", icon = R.drawable.receipt)
    data object SeeDetailDocument : Screen(route = "seedetaildocument_screen", name = "", icon = R.drawable.receipt)

    // 🔹 Insurance & Investment
    data object LifeInsurance : Screen(route = "life_insurance_screen", name = "Life Insurance", icon = R.drawable.ic_savings)
    data object PensionInsurance : Screen(route = "pension_insurance_screen", name = "Pension Insurance", icon = R.drawable.ic_pension)
    data object HealthInsurance : Screen(route = "health_insurance_screen", name = "Health Insurance", icon = R.drawable.ic_health)
    data object RMFFund : Screen(route = "rmf_fund_screen", name = "RMF Fund", icon = R.drawable.ic_rmf)
    data object SSFFund : Screen(route = "ssf_fund_screen", name = "SSF Fund", icon = R.drawable.ic_ssf)

    data object EditIncome : Screen(route = "editincome_screen", name = "EditIncome", icon = R.drawable.menu)
    data object EditTaxDeduc : Screen(route = "editTaxDeduc_screen", name="EditTax" , icon = R.drawable.menu )
}
