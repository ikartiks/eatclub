package au.com.eatclub.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Restaurant(
  @SerialName("objectId") var objectId: String,
  @SerialName("name") var name: String,
  @SerialName("address1") var address: String,
  @SerialName("suburb") var suburb: String,
  @SerialName("cuisines") var cuisines: ArrayList<String> = arrayListOf(),
  @SerialName("imageLink") var imageLink: String,
  @SerialName("open") var open: String,
  @SerialName("close") var close: String,
  @SerialName("deals") var deals: ArrayList<Deals> = arrayListOf()
)