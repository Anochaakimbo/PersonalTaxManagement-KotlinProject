package com.example.myproject.mainscreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun TaxAddScreen(navController: NavHostController) {
    val contextForToast = LocalContext.current.applicationContext
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "TaxAdd Screen"
        )
        Button(
            onClick = {
                Toast.makeText(contextForToast,"TaxAdd", Toast.LENGTH_SHORT).show()
            }){
            Text(text = "Click", fontSize = 25.sp)
        }
    }
}