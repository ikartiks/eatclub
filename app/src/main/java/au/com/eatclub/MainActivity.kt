package au.com.eatclub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import au.com.eatclub.presentation.screens.restaurant.list.RestaurantListScreen
import au.com.eatclub.presentation.theme.EatClubTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      EatClubTheme {
          RestaurantListScreen()
      }
    }
  }
}