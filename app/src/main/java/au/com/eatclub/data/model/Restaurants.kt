package au.com.eatclub.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Restaurants(
  @SerialName("restaurants") var restaurants: List<Restaurant> = arrayListOf()
)