package com.example.myproject.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myproject.loginandsignup.ForgetPasswordScreen
import com.example.myproject.loginandsignup.LoginScreen
import com.example.myproject.loginandsignup.RegisterScreen
import com.example.myproject.mainscreen.AddDeductionScreen
import com.example.myproject.mainscreen.AddIncomeScreen
import com.example.myproject.mainscreen.NotificationScreen
import com.example.myproject.mainscreen.ProfileScreen
import com.example.myproject.mainscreen.SearchScreen
import com.example.myproject.mainscreen.AddDeductionScreen
import com.example.myproject.mainscreen.DocumentScreen
import com.example.myproject.mainscreen.TaxSavingScreen
import com.example.myproject.mainscreen.UploadDocumentScreen
import com.example.savedocument.SeeDocumentScreen


@Composable
fun NavGraph(navController: NavHostController,modifier: Modifier,onLoginSuccess: () -> Unit){

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    )
    {
        composable(
            route = Screen.Home.route
        ){
            HomeScreen(navController)
        }
        composable(
            route = Screen.Search.route
        ){
            TaxSavingScreen(navController)
        }
        composable(
            route = Screen.Notification.route
        ){
            NotificationScreen(navController)
        }
        composable(
            route = Screen.Profile.route
        ){
            ProfileScreen(navController,Modifier)
        }
        composable(
            route = Screen.TaxDeduction.route
        ) {
            AddDeductionScreen(navController)
        }
        composable(
            route = Screen.Login.route
        ) {
            LoginScreen(navController,onLoginSuccess)
        }
        composable(
            route = Screen.Register.route
        ) {
            RegisterScreen(navController)
        }
        composable(
            route = Screen.Forgetpassword.route
        ) {
            ForgetPasswordScreen(navController)
        }
        composable(
            route = Screen.AddIncome.route
        ) {
            AddIncomeScreen(navController)
        }
        composable(
            route = "Showalldocument_screen"
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("user_id")?.toIntOrNull() ?: 1
            DocumentScreen(navController, userId)
        }

        composable(
            route = "${Screen.SaveDocument.route}/{selectedYear}"
        ) { backStackEntry ->
            val selectedYear = backStackEntry.arguments?.getString("selectedYear")?.toIntOrNull() ?: 2567
            UploadDocumentScreen(navController = navController, selectedYear = selectedYear)
        }

        composable(
            route = "showall_screen"
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("user_id")?.toIntOrNull() ?: 1
            DocumentScreen(navController = navController, userId = userId)
        }
        composable("Seedetaildocument_screen/{document_id}") { backStackEntry ->
            val documentId = backStackEntry.arguments?.getString("document_id")?.toIntOrNull() ?: 1
            SeeDocumentScreen(navController = navController, documentId = documentId)
        }
    }
}