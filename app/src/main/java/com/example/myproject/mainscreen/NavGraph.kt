package com.example.myproject.mainscreen

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


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
            HomeScreen()
        }
        composable(
            route = Screen.Search.route
        ){
            SearchScreen()
        }
        composable(
            route = Screen.Notification.route
        ){
            NotificationScreen()
        }
        composable(
            route = Screen.Profile.route
        ){
            ProfileScreen()
        }

    }
}