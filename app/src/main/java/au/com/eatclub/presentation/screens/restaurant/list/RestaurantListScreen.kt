package au.com.eatclub.presentation.screens.restaurant.list

import RestaurantCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import au.com.eatclub.R
import au.com.eatclub.presentation.screens.model.Restaurant
import au.com.eatclub.presentation.theme.EatClubTheme
import getDiscountTiming
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RestaurantListScreen(navController: NavController, vm: RestaurantListScreenViewModel) {
  when (val state = vm.state.collectAsState().value) {
    RestaurantListScreenState.Loading -> {
      LoadingScreen()
    }

    is RestaurantListScreenState.Failure -> {
      ErrorScreen(stringResource(R.string.something_went_wrong))
    }

    is RestaurantListScreenState.Success -> {
      RestaurantListSuccessScreen(
        vm::updateFilter,
        state.isFilterActive,
        state.restaurants,
        state.filteredRestaurants,
        onRestaurantClick = {
          vm.selectRestaurant(it)
          navController.navigate("restaurant_list/$it")
        }
      )
    }
  }

  LaunchedEffect("launch") {
    vm.getRestaurants()
  }
}

@Composable
fun RestaurantListSuccessScreen(
  onSearch: (String) -> Unit,
  isFilterActive: Boolean,
  restaurants: ImmutableList<Restaurant>,
  filteredRestaurants: ImmutableList<Restaurant>,
  onRestaurantClick: (String) -> Unit
) {
  Scaffold(
    contentWindowInsets = WindowInsets(0.dp),
    topBar = {
      TopBar(onSearch)
    },
    modifier = Modifier.padding(all = 10.dp)
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      val restaurantList = if (isFilterActive) filteredRestaurants else restaurants
      if (restaurantList.isEmpty()) {
        ErrorScreen(stringResource(R.string.no_data_found))
      } else {
        RestaurantList(
          restaurants = restaurantList,
          modifier = Modifier.padding(top = 20.dp),
          onRestaurantClick = onRestaurantClick
        )
      }
    }
  }
}


@Composable
@Preview(showBackground = true)
fun TopBarPreview() {
  EatClubTheme {
    Column {
      TopBar({})
    }
  }
}

@Composable
fun RestaurantList(
  restaurants: ImmutableList<Restaurant>,
  modifier: Modifier = Modifier,
  onRestaurantClick: (String) -> Unit
) {
  LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(10.dp)) {
    items(restaurants) { restaurant ->
      RestaurantCard(
        id = restaurant.id,
        name = restaurant.name,
        distance = restaurant.distance,
        cuisine = restaurant.cuisine,
        address = restaurant.address,
        options = restaurant.options,
        image = restaurant.image,
        discountPercent = restaurant.deals.first().discountPercent,
        discountTiming = getDiscountTiming(
          restaurant.deals.first().startTime,
          restaurant.deals.first().endTime
        ),
        modifier = Modifier,
        onClick = onRestaurantClick,
      )
    }
  }
}


@Preview(showBackground = true)
@Composable
fun RacingListScreenPreview() {
  EatClubTheme {
    Column {
      RestaurantList(
        restaurants = persistentListOf(
          Restaurant(
            id = "asd",
            name = "The Bar",
            distance = "0.5km",
            cuisine = "Italian",
            address = "Spensor st",
            options = persistentListOf("Dine in", "Take away"),
            openingTime = "5am",
            closingTime = "10pm",
            image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
            deals = persistentListOf(Restaurant.Deal("10", "10am", "10pm", "5"))
          ),
          Restaurant(
            id = "asd",
            name = "The Bar",
            distance = "0.5km",
            cuisine = "Italian",
            address = "Spensor st",
            openingTime = "5am",
            closingTime = "10pm",
            options = persistentListOf("Dine in", "Take away"),
            image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
            deals = persistentListOf(Restaurant.Deal("10", "10am", "10pm", "5"))
          ),
          Restaurant(
            id = "asd",
            name = "The Bar",
            distance = "0.5km",
            cuisine = "Italian",
            address = "Spensor st",
            openingTime = "5am",
            closingTime = "10pm",
            options = persistentListOf("Dine in", "Take away"),
            image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
            deals = persistentListOf(Restaurant.Deal("10", "10am", "10pm", "5"))
          )
        ),
        onRestaurantClick = {},
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
      )
    }
  }
}