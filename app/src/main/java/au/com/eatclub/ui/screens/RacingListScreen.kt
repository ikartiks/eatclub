package au.com.eatclub.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.eatclub.R
import au.com.eatclub.ui.screens.model.Restaurant
import au.com.eatclub.ui.theme.EatClubTheme
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RestaurantListScreen(restaurants: ImmutableList<Restaurant>, modifier:Modifier = Modifier) {
  Column (modifier = modifier){
    LazyColumn {
      items(restaurants) { restaurant ->
        RestaurantCard(
          id = restaurant.id,
          name = restaurant.name,
          distance = restaurant.distance,
          cuisine = restaurant.cuisine,
          address = restaurant.address,
          options = restaurant.options,
          image = restaurant.image,
          discountPercent = restaurant.discountPercent,
          discountTiming = restaurant.discountTiming,
          modifier = Modifier.padding(10.dp)
        )
      }
    }
  }

}

@Composable
fun RestaurantCard(
  id: String,
  name: String,
  distance: String,
  cuisine: String,
  address: String,
  options: ImmutableList<String>,
  image: String,
  discountPercent: String,
  discountTiming: String,
  modifier: Modifier = Modifier
) {
  Card (modifier = modifier,
    colors = CardDefaults.cardColors(containerColor = Color.Transparent)){
    Box{
      AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
          .data(image)
          .crossfade(true)
          .build(),
        error = painterResource(R.drawable.placeholder),
        placeholder = debugPlaceholder(R.drawable.placeholder),
        contentDescription = stringResource(R.string.distance),
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .fillMaxWidth()
          .height(260.dp)
          .clip(RoundedCornerShape(5)),
      )
      Column (modifier = Modifier.padding(
        start = 20.dp,
        top = 20.dp
      ).background(
        color = MaterialTheme.colorScheme.tertiary,
        shape = RoundedCornerShape(5.dp)
      ).padding(horizontal = 10.dp)){
       Text(
         stringResource(R.string.discount,discountPercent),
         style = MaterialTheme.typography.bodyMedium,
         color = MaterialTheme.colorScheme.onTertiary)
        Text(stringResource(R.string.discount_terms_anytime),//todo
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onTertiary)
      }
    }

    Text(text = name,
      style = MaterialTheme.typography.titleMedium)
    Row {
      Text(text = stringResource(R.string.distance, distance) ,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary)
      Text(text = address,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary)
    }

    Text(text = cuisine,
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onPrimary)
    OptionsBuilder(options)
    Spacer(modifier = Modifier.height(20.dp))
  }
}

@Composable
fun OptionsBuilder(options: ImmutableList<String>) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    options.forEachIndexed { index, option ->
      Text(text = option,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onPrimary)
      if(index!=options.size-1){
        Icon(
          imageVector = Icons.Default.CheckCircle,
          tint = MaterialTheme.colorScheme.onPrimary,
          contentDescription = "",
          modifier = Modifier
            .size(20.dp)
            .padding(horizontal = 5.dp)
        )
      }
    }
  }
}

@Composable
@Preview
fun RacingListScreenPreview() {
  EatClubTheme{
    Column {
      RestaurantListScreen(
        persistentListOf(Restaurant(id = "asd",
          name = "The Bar",
          distance = "0.5km",
          cuisine = "Italian",
          address = "Spensor st",
          options = persistentListOf("Dine in", "Take away"),
          image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
          discountPercent = "30",
          discountTiming = "All day",),
          Restaurant(id = "asd",
            name = "The Bar",
            distance = "0.5km",
            cuisine = "Italian",
            address = "Spensor st",
            options = persistentListOf("Dine in", "Take away"),
            image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
            discountPercent = "30",
            discountTiming = "All day",),
          Restaurant(id = "asd",
          name = "The Bar",
          distance = "0.5km",
          cuisine = "Italian",
          address = "Spensor st",
          options = persistentListOf("Dine in", "Take away"),
          image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
          discountPercent = "30",
          discountTiming = "All day",)),
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
      )
    }
  }
}

@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) =
  if (LocalInspectionMode.current) {
    painterResource(id = debugPreview)
  } else {
    null
  }