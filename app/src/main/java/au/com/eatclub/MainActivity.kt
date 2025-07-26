package au.com.eatclub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import au.com.eatclub.presentation.graph.MAIN_GRAPH_NAME
import au.com.eatclub.presentation.graph.mainGraph
import au.com.eatclub.presentation.theme.EatClubTheme

class MainActivity : ComponentActivity() {

  private lateinit var navController: NavHostController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      navController = rememberNavController()
      EatClubTheme {
        NavHost(
          navController,
          startDestination = MAIN_GRAPH_NAME,
          modifier = Modifier
        ) {
          mainGraph(navController = navController)
        }
      }
    }
  }
}