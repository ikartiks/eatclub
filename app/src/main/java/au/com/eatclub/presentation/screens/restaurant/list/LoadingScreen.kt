package au.com.eatclub.presentation.screens.restaurant.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LoadingScreen(){
  Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween) {
    CircularProgressIndicator()
  }
}

@Composable
fun ErrorScreen(message:String ){
  Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.SpaceBetween) {
    Text(message)
  }
}