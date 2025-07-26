package au.com.eatclub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import au.com.eatclub.presentation.screens.model.Restaurant
import au.com.eatclub.presentation.screens.restaurant.detail.RestaurantDetail
import au.com.eatclub.presentation.screens.restaurant.list.RestaurantList
import au.com.eatclub.presentation.theme.EatClubTheme
import kotlinx.collections.immutable.persistentListOf
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.AutoCloseKoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class RestaurantListScreenShotTest : AutoCloseKoinTest() {
  @Test
  fun testIsRestaurantListDisplayedCorrectly() {
    runScreenshotTest(heightDp = 1600) {
      EatClubTheme {
        Column(
          modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(40.dp),
        ) {
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
    }
  }

  @Test
  fun testIsRestaurantDetailDisplayedCorrectly() {
    runScreenshotTest(heightDp = 1600) {
      EatClubTheme {
        Column(
          modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(40.dp),
        ) {
          Column {
            val restaurant = Restaurant(
              id = "asd",
              name = "The Bar",
              distance = "0.5km",
              cuisine = "Italian",
              address = "Spensor st",
              options = persistentListOf("Dine in", "Take away"),
              openingTime = "5am",
              closingTime = "10pm",
              image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
              deals = persistentListOf(
                Restaurant.Deal("20", "10am", "10pm", "5"),
                Restaurant.Deal("10", "10am", "10pm", "5")
              )
            )
            RestaurantDetail(
              image = restaurant.image,
              name = restaurant.name,
              options = restaurant.options,
              time = "5pm to 10pm",
              address = restaurant.address,
              deals = restaurant.deals,
            )
          }
        }
      }
    }
  }
}