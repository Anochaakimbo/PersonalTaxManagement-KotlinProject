package com.example.myproject

import androidx.annotation.DrawableRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route :String,val name:String ,@DrawableRes val icon: Int) {
    data object Home : Screen(route = "home_screen", name = "",icon = R.drawable.home)
    data object Search : Screen(route = "search_screen", name = "",icon = R.drawable.analyzer)
    data object TaxAdd : Screen(route = "taxadd_screen", name = "",icon = R.drawable.add)
    data object Notification : Screen(route = "notification", name = "",icon = R.drawable.bell)
    data object Profile : Screen(route = "profile_screen", name = "",icon = R.drawable.menu)
}