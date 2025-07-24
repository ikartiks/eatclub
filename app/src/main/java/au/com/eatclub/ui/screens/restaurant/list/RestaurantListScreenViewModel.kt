package au.com.eatclub.ui.screens.restaurant.list

import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.eatclub.ui.screens.model.Restaurant
import java.util.Locale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class RestaurantListScreenViewModel(
  val savedStateHandle: SavedStateHandle,
) : ViewModel(), KoinComponent {

  private val _state =
    MutableStateFlow<RestaurantListScreenState>(RestaurantListScreenState.Loading)
  val state: StateFlow<RestaurantListScreenState> = _state


  fun updateFilter(search: String) = viewModelScope.launch{
    if(search.isNullOrBlank()){
      _state.update { data ->
        val successSate = (data as? RestaurantListScreenState.Success)
        successSate?.let {
          return@update it.copy(isFilterActive = false, it.restaurants, persistentListOf())
        }?: data
      }
    }else{
      _state.update { data ->
        val successSate = (data as? RestaurantListScreenState.Success)
        successSate?.let {
          return@update it.copy(
            isFilterActive = true,
            restaurants = it.restaurants,
            filteredRestaurants = it.restaurants.filter {
              it.cuisine.lowercase(Locale.getDefault()).contains(search.lowercase(Locale.getDefault())) ||
                  it.name.lowercase(Locale.getDefault()).contains(search.lowercase(Locale.getDefault()))
            }.toImmutableList()
          )
        }
        data
      }
    }

  }

  suspend fun getRestaurants() = viewModelScope.launch {
    _state.value = RestaurantListScreenState.Success(
      false, persistentListOf(
        Restaurant(
          id = "asd",
          name = "The Bar",
          distance = "0.5km",
          cuisine = "Italian",
          address = "Spensor st",
          options = persistentListOf("Dine in", "Take away"),
          image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
          discountPercent = "30",
          discountTiming = "All day",
        ),
        Restaurant(
          id = "asd",
          name = "POP IN",
          distance = "0.5km",
          cuisine = "Indian",
          address = "Spensor st",
          options = persistentListOf("Dine in", "Take away"),
          image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
          discountPercent = "30",
          discountTiming = "All day",
        ),
        Restaurant(
          id = "asd",
          name = "The Bar at abc",
          distance = "0.5km",
          cuisine = "Japanese",
          address = "Spensor st",
          options = persistentListOf("Dine in", "Take away"),
          image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
          discountPercent = "30",
          discountTiming = "All day",
        )
      ),
      persistentListOf()
    )
  }


}

sealed interface RestaurantListScreenState {
  data class Success(
    val isFilterActive: Boolean,
    val restaurants: ImmutableList<Restaurant>,
    val filteredRestaurants: ImmutableList<Restaurant>
  ) : RestaurantListScreenState

  data class Failure(
    val reason: String,
  ) : RestaurantListScreenState

  data object Loading : RestaurantListScreenState
}
