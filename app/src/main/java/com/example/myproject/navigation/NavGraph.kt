package com.example.myproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myproject.loginandsignup.ForgetPasswordScreen
import com.example.myproject.loginandsignup.LoginScreen
import com.example.myproject.loginandsignup.RegisterScreen
import com.example.myproject.loginandsignup.SharedPreferencesManager
import com.example.myproject.mainscreen.AddIncomeScreen
import com.example.myproject.mainscreen.HomeScreen
import com.example.myproject.mainscreen.NotificationScreen
import com.example.myproject.mainscreen.ProfileScreen
import com.example.myproject.mainscreen.SearchScreen
import com.example.myproject.mainscreen.TaxDeductionScreen
import com.example.myproject.profilesubscreen.ContactUsScreen
import com.example.myproject.profilesubscreen.EditScreen
import com.example.myproject.profilesubscreen.PrivacyAndPermissionsScreen
import com.example.myproject.profilesubscreen.SecureScreen


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
        //Profile
        composable(
            route = Screen.EditProfileScreen.route
        ) {
            EditScreen(navController)
        }

        composable(
            route = Screen.SecureScreen.route
        ) {
            SecureScreen(navController)
        }

        composable(
            route = Screen.PrivacyScreen.route
        ) {
            PrivacyAndPermissionsScreen(navController)
        }

        composable(
            route = Screen.ContactScreen.route
        ) {
            ContactUsScreen(navController)
        }

    }
}