package com.example.myproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myproject.loginandsignup.ForgetPasswordScreen
import com.example.myproject.loginandsignup.LoginScreen
import com.example.myproject.loginandsignup.RegisterScreen
import com.example.myproject.mainscreen.AddIncomeScreen
import com.example.myproject.mainscreen.DocumentScreen
import com.example.myproject.mainscreen.HealthInsuranceScreen
import com.example.myproject.mainscreen.HomeScreen
import com.example.myproject.mainscreen.LifeInsuranceScreen
import com.example.myproject.mainscreen.NotificationScreen
import com.example.myproject.mainscreen.PensionInsuranceScreen
import com.example.myproject.mainscreen.ProfileScreen
import com.example.myproject.mainscreen.RMFFundScreen
import com.example.myproject.mainscreen.SSFFundScreen
import com.example.myproject.mainscreen.SearchScreen
//import com.example.myproject.mainscreen.SearchScreen
import com.example.myproject.mainscreen.TaxDeductionScreen
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
            SearchScreen(navController)
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
            TaxDeductionScreen(navController)
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

        composable(Screen.TaxSaving.route
        ) {
            TaxSavingScreen(navController)
        }

        composable(route = Screen.LifeInsurance.route) {
            LifeInsuranceScreen(navController)
        }
        composable(route = Screen.PensionInsurance.route) {
            PensionInsuranceScreen(navController)
        }
        composable(route = Screen.HealthInsurance.route) {
            HealthInsuranceScreen(navController)
        }
        composable(route = Screen.RMFFund.route) {
            RMFFundScreen(navController)
        }
        composable(route = Screen.SSFFund.route) {
            SSFFundScreen(navController)
        }

        composable("taxSaving") {
            TaxSavingScreen(navController)
        }
        composable("lifeInsurance") {
            LifeInsuranceScreen(navController)
        }
        composable("pensionInsurance") {
            PensionInsuranceScreen(navController)
        }
        composable("healthInsurance") {
            HealthInsuranceScreen(navController)
        }
        composable("rmfFund") {
            RMFFundScreen(navController)
        }
        composable("ssfFund") {
            SSFFundScreen(navController)
        }

        composable(
            route = "Showalldocument_screen"
        ) {
            DocumentScreen(navController)
        }
        composable(
            route = Screen.SaveDocument.route
        ) {
            UploadDocumentScreen(navController)
        }
        composable(
            route = Screen.Seedetaildocument.route
        ) {
            SeeDocumentScreen(navController)
        }

    }
}