package com.example.myproject.screen

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myproject.mainscreen.HomeScreen
import com.example.myproject.mainscreen.NotificationScreen
import com.example.myproject.mainscreen.ProfileScreen
import com.example.myproject.mainscreen.Screen
import com.example.myproject.mainscreen.SearchScreen

@Composable
fun NavGraphForAfterLogin(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
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
            ProfileScreen(navController)
        }

    }
}