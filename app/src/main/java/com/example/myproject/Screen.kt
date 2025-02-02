package com.example.myproject

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route :String,val name:String , val icon: ImageVector) {
    data object Home : Screen(route = "home_screen", name = "Home",icon = Icons.Default.Home)
    data object Search : Screen(route = "search_screen", name = "Search",icon = Icons.Default.Search)
    data object TaxAdd : Screen(route = "taxadd_screen", name = "TaxAdd",icon = Icons.Default.Favorite)
    data object Notification : Screen(route = "notification", name = "Notification",icon = Icons.Default.Notifications)
    data object Profile : Screen(route = "profile_screen", name = "Profile",icon = Icons.Default.Person)
}