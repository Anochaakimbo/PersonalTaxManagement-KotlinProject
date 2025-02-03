package com.example.myproject.screen

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

@Composable
fun ProfileScreen(){
    val contextForToast = LocalContext.current.applicationContext
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text = "Profile Screen"
        )
        Button(
            onClick = {
                Toast.makeText(contextForToast,"Profile", Toast.LENGTH_SHORT).show()
            }){
            Text(text = "Click", fontSize = 25.sp)
        }
    }
}