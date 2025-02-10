package com.example.myproject.screen

import androidx.compose.runtime.Composable

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navController: NavHostController){
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