package au.com.eatclub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import au.com.eatclub.ui.screens.restaurant.list.RestaurantListScreen
import au.com.eatclub.ui.theme.EatClubTheme

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