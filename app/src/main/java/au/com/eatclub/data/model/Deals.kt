package au.com.eatclub.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Deals(
  @SerialName("objectId") var objectId: String,
  @SerialName("discount") var discount: String,
  @SerialName("dineIn") var dineIn: String,
  @SerialName("lightning") var lightning: String,
  @SerialName("open") var open: String? = null,
  @SerialName("close") var close: String? = null,
  @SerialName("start") var start: String? = null,
  @SerialName("end") var end: String? = null,
  @SerialName("qtyLeft") var qtyLeft: String
)