package com.example.myproject.screen

import androidx.annotation.DrawableRes
import com.example.myproject.R

sealed class Screen(val route :String,val name:String ,@DrawableRes val icon: Int) {
    data object Home : Screen(route = "home_screen", name = "",icon = R.drawable.home)
    data object Search : Screen(route = "search_screen", name = "",icon = R.drawable.analyzer)
    data object TaxAdd : Screen(route = "taxadd_screen", name = "",icon = R.drawable.add)
    data object Notification : Screen(route = "notification", name = "",icon = R.drawable.bell)
    data object Profile : Screen(route = "profile_screen", name = "",icon = R.drawable.menu)
}