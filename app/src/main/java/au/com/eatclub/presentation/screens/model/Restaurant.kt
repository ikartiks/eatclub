package au.com.eatclub.presentation.screens.model

import kotlinx.collections.immutable.ImmutableList

data class Restaurant(
  val id:String,
  val name: String,
  val distance: String,
  val cuisine: String,
  val address: String,
  val options: ImmutableList<String>,
  val image:String,
  val deals: ImmutableList<Deal>,
  val openingTime: String,
  val closingTime: String,
) {
  data class Deal(
    val discountPercent: String,
    val startTime: String?,
    val endTime: String?,
    val quantityLeft: String
  )
}