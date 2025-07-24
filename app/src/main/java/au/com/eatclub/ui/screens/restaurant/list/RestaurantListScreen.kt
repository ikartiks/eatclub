package au.com.eatclub.ui.screens.restaurant.list

import RestaurantCard
import androidx.annotation.DrawableRes
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
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.eatclub.R
import au.com.eatclub.ui.screens.model.Restaurant
import au.com.eatclub.ui.theme.EatClubTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun RestaurantListScreen(vm: RestaurantListScreenViewModel = koinViewModel()) {
  val state = vm.state.collectAsState().value
  when(state){
    RestaurantListScreenState.Loading ->{
      LoadingScreen()
    }

    is RestaurantListScreenState.Failure -> {
      ErrorScreen(stringResource(R.string.something_went_wrong))
    }
    is RestaurantListScreenState.Success -> {
      RestaurantListSuccessScreen(vm::updateFilter,state.isFilterActive,state.restaurants,state.filteredRestaurants)
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
  filteredRestaurants: ImmutableList<Restaurant>
){
  Scaffold(
    contentWindowInsets = WindowInsets(0.dp),
    topBar = {
      TopBar(onSearch)
    },
    modifier = Modifier.padding(all = 10.dp)
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      val restaurantList = if(isFilterActive) filteredRestaurants else restaurants
      if(restaurantList.isEmpty()){
        ErrorScreen(stringResource(R.string.no_data_found))
      }else{
        RestaurantList(
          restaurantList,
          modifier = Modifier.padding(top = 20.dp)
        )
      }
    }
  }
}



@Composable
@Preview(showBackground = true)
fun TopBarPreview(){
  EatClubTheme {
    Column {
      TopBar({})
    }
  }
}

@Composable
fun RestaurantList(restaurants: ImmutableList<Restaurant>, modifier:Modifier = Modifier){
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
          discountPercent = restaurant.discountPercent,
          discountTiming = restaurant.discountTiming,
          modifier = Modifier
        )
      }
    }
}

@Composable
@Preview(showBackground = true)
fun RacingListScreenPreview() {
  EatClubTheme{
    Column {
      RestaurantList(
        persistentListOf(Restaurant(id = "asd",
          name = "The Bar",
          distance = "0.5km",
          cuisine = "Italian",
          address = "Spensor st",
          options = persistentListOf("Dine in", "Take away"),
          image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
          discountPercent = "30",
          discountTiming = "All day",),
          Restaurant(id = "asd",
            name = "The Bar",
            distance = "0.5km",
            cuisine = "Italian",
            address = "Spensor st",
            options = persistentListOf("Dine in", "Take away"),
            image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
            discountPercent = "30",
            discountTiming = "All day",),
          Restaurant(id = "asd",
          name = "The Bar",
          distance = "0.5km",
          cuisine = "Italian",
          address = "Spensor st",
          options = persistentListOf("Dine in", "Take away"),
          image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
          discountPercent = "30",
          discountTiming = "All day",)),
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
      )
    }
  }
}



@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) =
  if (LocalInspectionMode.current) {
    painterResource(id = debugPreview)
  } else {
    null
  }