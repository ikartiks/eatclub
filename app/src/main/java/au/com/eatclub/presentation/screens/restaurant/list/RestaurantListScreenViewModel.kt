package au.com.eatclub.presentation.screens.restaurant.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import au.com.eatclub.data.model.Deals
import au.com.eatclub.data.repo.RestaurantListRepository
import au.com.eatclub.presentation.screens.model.Restaurant
import java.util.Locale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class RestaurantListScreenViewModel(
  private val restaurantListRepository: RestaurantListRepository,
) : ViewModel(), KoinComponent {

  private val _state =
    MutableStateFlow<RestaurantListScreenState>(RestaurantListScreenState.Loading)
  val state: StateFlow<RestaurantListScreenState> = _state

  var selectedRestaurant: Restaurant? = null

  fun selectRestaurant(id: String) {
    val stateValue = state.value
    if (stateValue is RestaurantListScreenState.Success) {
      selectedRestaurant = stateValue.restaurants.first { it.id == id }
    }
  }

  fun updateFilter(search: String) = viewModelScope.launch {
    if (search.isBlank()) {
      _state.update { data ->
        val successSate = (data as? RestaurantListScreenState.Success)
        successSate?.let {
          return@update it.copy(
            isFilterActive = false,
            restaurants = it.restaurants,
            filteredRestaurants = persistentListOf()
          )
        } ?: data
      }
    } else {
      _state.update { data ->
        val successSate = (data as? RestaurantListScreenState.Success)
        successSate?.let {
          return@update it.copy(
            isFilterActive = true,
            restaurants = it.restaurants,
            filteredRestaurants = it.restaurants.filter {
              it.cuisine.lowercase(Locale.getDefault())
                .contains(search.lowercase(Locale.getDefault())) ||
                  it.name.lowercase(Locale.getDefault())
                    .contains(search.lowercase(Locale.getDefault()))
            }.toImmutableList()
          )
        }
        data
      }
    }
  }

  fun getRestaurants() = viewModelScope.launch {
    restaurantListRepository.fetchAllRestaurants()?.let {
      _state.value = RestaurantListScreenState.Success(
        isFilterActive = false,
        restaurants = it.restaurants.map { it.toRestaurant() }
          .sortedByDescending {
            it.deals.maxBy { deal ->
              deal.discountPercent
            }.discountPercent
          }.toImmutableList(),
        filteredRestaurants = persistentListOf()
      )
    }
  }
}

private fun au.com.eatclub.data.model.Restaurant.toRestaurant() =
  Restaurant(
    id = objectId,
    name = name,
    distance = "TBA",
    cuisine = cuisines.joinToString(", "),
    address = "$address, $suburb",
    options = getOptions(deals.maxBy { it.discount }),
    image = imageLink,
    openingTime = open,
    closingTime = close,
    deals = deals.map { it.toDeal() }.sortedByDescending { it.discountPercent }.toImmutableList()
  )

private fun Deals.toDeal(): Restaurant.Deal = Restaurant.Deal(
  discountPercent = discount,
  startTime = start ?: open,
  endTime = end ?: close,
  quantityLeft = qtyLeft
)

private fun getOptions(deal: Deals): ImmutableList<String> {
  val options = mutableListOf<String>()
  if (deal.dineIn == "true")
    options.add("Dine in")
  if (deal.lightning == "true")
    options.add("Take away")
  return options.toImmutableList()
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
