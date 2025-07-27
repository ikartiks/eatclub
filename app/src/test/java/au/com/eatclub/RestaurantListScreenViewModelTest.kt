package au.com.eatclub

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import au.com.eatclub.data.model.Deals
import au.com.eatclub.data.model.Restaurant
import au.com.eatclub.data.model.Restaurants
import au.com.eatclub.data.repo.RestaurantListRepository
import au.com.eatclub.presentation.screens.restaurant.list.RestaurantListScreenState
import au.com.eatclub.presentation.screens.restaurant.list.RestaurantListScreenViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest

@OptIn(ExperimentalCoroutinesApi::class)
class RestaurantListScreenViewModelTest : KoinTest {

  private val restaurantListRepository: RestaurantListRepository = mockk(relaxed = true)

  private val vm: RestaurantListScreenViewModel =
    RestaurantListScreenViewModel(restaurantListRepository)

  @Rule
  @JvmField
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @OptIn(ExperimentalCoroutinesApi::class)
  val dispatcher = UnconfinedTestDispatcher()

  @Before
  fun setupThreads() {
    Dispatchers.setMain(dispatcher)
  }

  @After
  fun tearDownThreads() {
    Dispatchers.resetMain()
  }


  @Test
  fun testSuccessState() {

    val restaurants = Restaurants(
      restaurants = arrayListOf(
        Restaurant(
          objectId = "asd",
          name = "The Bar",
          address = "Spensor st",
          suburb = "4122",
          cuisines = arrayListOf("Italian", "Indian"),
          imageLink = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
          open = "5am",
          close = "10pm",
          deals = arrayListOf(
            Deals(
              objectId = "asd", discount = "50", dineIn = "true",
              lightning = "true",
              open = "5am",
              close = "10pm",
              start = "5am",
              end = "10pm",
              qtyLeft = "5"
            )
          ),
        )
      )
    )
    coEvery { restaurantListRepository.fetchAllRestaurants() } returns restaurants

    vm.getRestaurants()
    assertEquals(
      expected = "The Bar",
      actual = (vm.state.value as? RestaurantListScreenState.Success)?.restaurants?.first()?.name
    )
  }
}