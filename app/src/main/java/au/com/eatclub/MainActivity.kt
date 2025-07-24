package au.com.eatclub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import au.com.eatclub.ui.screens.RestaurantCard
import au.com.eatclub.ui.screens.RestaurantListScreen
import au.com.eatclub.ui.screens.model.Restaurant
import au.com.eatclub.ui.theme.EatClubTheme
import kotlinx.collections.immutable.immutableListOf
import kotlinx.collections.immutable.persistentListOf

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      EatClubTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          RestaurantListScreen(
            persistentListOf(
              Restaurant(id = "asd",
            name = "The Bar",
            distance = "0.5km",
            cuisine = "Italian",
            address = "Spensor st",
            options = persistentListOf("Dine in", "Take away"),
            image = "https://demo.eccdn.com.au/images/D80263E8-FD89-2C70-FF6B-D854ADB8DB00/eatclub_1634706351211.jpg",
            discountPercent = "30",
            discountTiming = "All day",)
            ), modifier = Modifier.padding(innerPadding)
          )
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
    text = "Hello $name!",
    modifier = modifier
  )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  EatClubTheme {
    Greeting("Android")
  }
}