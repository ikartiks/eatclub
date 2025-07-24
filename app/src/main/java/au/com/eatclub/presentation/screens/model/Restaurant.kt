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
  val discountPercent:String,
  val discountTiming:String
)