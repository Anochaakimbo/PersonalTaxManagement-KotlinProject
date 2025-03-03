//package com.example.myproject.navigation
//
//import HomeScreen
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.example.myproject.loginandsignup.ForgetPasswordScreen
//import com.example.myproject.loginandsignup.LoginScreen
//import com.example.myproject.loginandsignup.RegisterScreen
//import com.example.myproject.mainscreen.AddIncomeScreen
//import com.example.myproject.mainscreen.DocumentScreen
//import com.example.myproject.mainscreen.HealthInsuranceScreen
//import com.example.myproject.mainscreen.HomeScreen
//import com.example.myproject.mainscreen.LifeInsuranceScreen
//import com.example.myproject.mainscreen.NotificationScreen
//import com.example.myproject.mainscreen.PensionInsuranceScreen
//import com.example.myproject.mainscreen.ProfileScreen
//import com.example.myproject.mainscreen.RMFFundScreen
//import com.example.myproject.mainscreen.SSFFundScreen
//import com.example.myproject.mainscreen.SearchScreen
////import com.example.myproject.mainscreen.SearchScreen
//import com.example.myproject.mainscreen.TaxDeductionScreen
//import com.example.myproject.mainscreen.TaxSavingScreen
//import com.example.myproject.mainscreen.UploadDocumentScreen
//import com.example.savedocument.SeeDocumentScreen
//
//
//@Composable
//fun NavGraph(navController: NavHostController,modifier: Modifier,onLoginSuccess: () -> Unit){
//
//    NavHost(
//        navController = navController,
//        startDestination = Screen.Login.route
//    )
//    {
//        composable(
//            route = Screen.Home.route
//        ){
//            HomeScreen(navController)
//        }
//        composable(
//            route = Screen.Search.route
//        ){
//            SearchScreen(navController)
//        }
//        composable(
//            route = Screen.Notification.route
//        ){
//            NotificationScreen(navController)
//        }
//        composable(
//            route = Screen.Profile.route
//        ){
//            ProfileScreen(navController,Modifier)
//        }
//        composable(
//            route = Screen.TaxDeduction.route
//        ) {
//            TaxDeductionScreen(navController)
//        }
//        composable(
//            route = Screen.Login.route
//        ) {
//            LoginScreen(navController,onLoginSuccess)
//        }
//        composable(
//            route = Screen.Register.route
//        ) {
//            RegisterScreen(navController)
//        }
//        composable(
//            route = Screen.Forgetpassword.route
//        ) {
//            ForgetPasswordScreen(navController)
//        }
//        composable(
//            route = Screen.AddIncome.route
//        ) {
//            AddIncomeScreen(navController)
//        }
//
//        composable(Screen.TaxSaving.route
//        ) {
//            TaxSavingScreen(navController)
//        }
//
//        composable(route = Screen.LifeInsurance.route) {
//            LifeInsuranceScreen(navController)
//        }
//        composable(route = Screen.PensionInsurance.route) {
//            PensionInsuranceScreen(navController)
//        }
//        composable(route = Screen.HealthInsurance.route) {
//            HealthInsuranceScreen(navController)
//        }
//        composable(route = Screen.RMFFund.route) {
//            RMFFundScreen(navController)
//        }
//        composable(route = Screen.SSFFund.route) {
//            SSFFundScreen(navController)
//        }
//
//        composable("taxSaving") {
//            TaxSavingScreen(navController)
//        }
//        composable("lifeInsurance") {
//            LifeInsuranceScreen(navController)
//        }
//        composable("pensionInsurance") {
//            PensionInsuranceScreen(navController)
//        }
//        composable("healthInsurance") {
//            HealthInsuranceScreen(navController)
//        }
//        composable("rmfFund") {
//            RMFFundScreen(navController)
//        }
//        composable("ssfFund") {
//            SSFFundScreen(navController)
//        }
//
//
//
//        composable(
//            route = "Showalldocument_screen"
//        ) { backStackEntry ->
//            val userId = backStackEntry.arguments?.getString("user_id")?.toIntOrNull() ?: 1
//            DocumentScreen(navController, userId)
//        }
//
//        composable(
//            route = "${Screen.SaveDocument.route}/{selectedYear}"
//        ) { backStackEntry ->
//            val selectedYear = backStackEntry.arguments?.getString("selectedYear")?.toIntOrNull() ?: 2567
//            UploadDocumentScreen(navController = navController, selectedYear = selectedYear)
//        }
//
//        composable(
//            route = "showall_screen"
//        ) { backStackEntry ->
//            val userId = backStackEntry.arguments?.getString("user_id")?.toIntOrNull() ?: 1
//            DocumentScreen(navController = navController, userId = userId)
//        }
//
//
//        composable("Seedetaildocument_screen/{document_id}") { backStackEntry ->
//            val documentId = backStackEntry.arguments?.getString("document_id")?.toIntOrNull() ?: 1
//            SeeDocumentScreen(navController = navController, documentId = documentId)
//        }
//
//        composable("Seedetaildocument_screen/{document_id}") { backStackEntry ->
//            val documentId = backStackEntry.arguments?.getString("document_id")?.toIntOrNull() ?: 1
//            SeeDocumentScreen(navController = navController, documentId = documentId)
//        }
//
//
//    }
//}

package com.example.myproject.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myproject.loginandsignup.ForgetPasswordScreen
import com.example.myproject.loginandsignup.LoginScreen
import com.example.myproject.loginandsignup.RegisterScreen
import com.example.myproject.loginandsignup.ResetPasswordScreen
import com.example.myproject.mainscreen.AddDeductionScreen
import com.example.myproject.mainscreen.AddIncomeScreen
import com.example.myproject.mainscreen.NotificationScreen
import com.example.myproject.mainscreen.ProfileScreen
import com.example.myproject.mainscreen.SearchScreen
import com.example.myproject.mainscreen.AddDeductionScreen
import com.example.myproject.mainscreen.DocumentScreen
import com.example.myproject.mainscreen.HealthInsuranceScreen
import com.example.myproject.mainscreen.LifeInsuranceScreen
import com.example.myproject.mainscreen.PensionInsuranceScreen
import com.example.myproject.mainscreen.RMFFundScreen
import com.example.myproject.mainscreen.SSFFundScreen
import com.example.myproject.mainscreen.TaxSavingScreen
import com.example.myproject.mainscreen.UploadDocumentScreen
import com.example.savedocument.SeeDocumentScreen
import com.example.myproject.profilesubscreen.ContactUsScreen
import com.example.myproject.profilesubscreen.EditScreen
import com.example.myproject.profilesubscreen.PrivacyAndPermissionsScreen
import com.example.myproject.profilesubscreen.SecureScreen


@Composable
fun NavGraph(navController: NavHostController,modifier: Modifier,onLoginSuccess: () -> Unit,onLogout: () -> Unit){

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
            ProfileScreen(navController,Modifier, onLogout)
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
            route = Screen.ForgetPassword.route
        ) {
            ForgetPasswordScreen(navController)
        }
        composable(
            route = Screen.AddIncome.route
        ) {
            AddIncomeScreen(navController)
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

        composable("Seedetaildocument_screen/{document_id}") { backStackEntry ->
            val documentId = backStackEntry.arguments?.getString("document_id")?.toIntOrNull() ?: 1
            SeeDocumentScreen(navController = navController, documentId = documentId)
        }

        composable(
            route = "${Screen.SaveDocument.route}/{selectedYear}"
        ) { backStackEntry ->
            val selectedYear = backStackEntry.arguments?.getString("selectedYear")?.toIntOrNull() ?: 2567
            UploadDocumentScreen(navController = navController, selectedYear = selectedYear)
        }

        composable(
            route = Screen.ResetPassword.route,   // 👈 ต้องกำหนด `{email}` ด้วย
            arguments = listOf(navArgument("email") { type = NavType.StringType })  // รับค่า email
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""  // รับค่าจาก URL
            ResetPasswordScreen(navController, email)  // ส่ง email เข้าไปในหน้า ResetPassword
        }
    }



}