package au.com.eatclub.presentation.screens.model

import au.com.eatclub.data.model.Deals
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

internal fun au.com.eatclub.data.model.Restaurant.toRestaurant() =
  Restaurant(
    id = objectId,
    name = name,
    distance = "",
    cuisine = cuisines.joinToString(", "),
    address = "$address, $suburb",
    options = getOptions(deals.maxBy { it.discount }),
    image = imageLink,
    openingTime = open,
    closingTime = close,
    deals = deals.map { it.toDeal() }.sortedByDescending { it.discountPercent }.toImmutableList()
  )

internal fun Deals.toDeal(): Restaurant.Deal = Restaurant.Deal(
  discountPercent = discount,
  startTime = start ?: open,
  endTime = end ?: close,
  quantityLeft = qtyLeft
)

internal fun getOptions(deal: Deals): ImmutableList<String> {
  val options = mutableListOf<String>()
  if (deal.dineIn == "true")
    options.add("Dine in")
  if (deal.lightning == "true")
    options.add("Take away")
  return options.toImmutableList()
}