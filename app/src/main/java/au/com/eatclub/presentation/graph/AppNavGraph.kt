package au.com.eatclub.presentation.graph

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import au.com.eatclub.presentation.screens.restaurant.detail.RestaurantDetailScreen
import au.com.eatclub.presentation.screens.restaurant.list.RestaurantListScreen
import au.com.eatclub.presentation.screens.restaurant.list.RestaurantListScreenViewModel
import org.koin.androidx.compose.koinViewModel


const val RESTAURANT_LIST_SCREEN = "restaurant_list"
const val MAIN_GRAPH_NAME = "main_graph"

fun NavGraphBuilder.mainGraph(navController: NavController) {

  navigation(startDestination = RESTAURANT_LIST_SCREEN, route = MAIN_GRAPH_NAME) {
    composable(RESTAURANT_LIST_SCREEN) { backStackEntry ->
      val storeOwnerKey =
        remember(backStackEntry) { navController.getBackStackEntry(MAIN_GRAPH_NAME) }
      val vm: RestaurantListScreenViewModel = koinViewModel(viewModelStoreOwner = storeOwnerKey)
      RestaurantListScreen(navController, vm)
    }
    composable(
      "$RESTAURANT_LIST_SCREEN/{restaurantId}",
      arguments = listOf(
        navArgument("restaurantId") {
          type = NavType.StringType
        }
      )
    ) { backStackEntry ->
      val storeOwnerKey =
        remember(backStackEntry) { navController.getBackStackEntry(MAIN_GRAPH_NAME) }
      val vm: RestaurantListScreenViewModel = koinViewModel(viewModelStoreOwner = storeOwnerKey)
      RestaurantDetailScreen(
        navController = navController,
        vm
      )
    }
  }
}