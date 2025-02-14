package com.example.myproject.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myproject.MyScaffoldLayout
import com.example.myproject.loginandsignup.ForgetPassword
import com.example.myproject.loginandsignup.LoginScreen
import com.example.myproject.loginandsignup.RegisterScreen
import com.example.myproject.mainscreen.TaxSavingScreen



@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("main") { MyScaffoldLayout() }
        composable("register") { RegisterScreen (navController)}
        composable("forgetpassword") { ForgetPassword (navController)}

        composable("tax_saving") { TaxSavingScreen() }    // ✅ เพิ่มเส้นทางไป TaxSavingScreen
    }
}

