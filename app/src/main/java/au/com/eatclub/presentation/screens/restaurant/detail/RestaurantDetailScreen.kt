package au.com.eatclub.presentation.screens.restaurant.detail

import OptionsBuilder
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import au.com.eatclub.R
import au.com.eatclub.presentation.screens.model.Restaurant
import au.com.eatclub.presentation.screens.restaurant.list.RestaurantListScreenViewModel
import au.com.eatclub.presentation.screens.restaurant.list.TopBar
import au.com.eatclub.presentation.theme.EatClubTheme
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import getDiscountTiming
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RestaurantDetailScreen(navController: NavController, vm: RestaurantListScreenViewModel) {
  Scaffold(
    contentWindowInsets = WindowInsets(0.dp),
    topBar = {
      TopBar({}, showSearch = false)
    },
    modifier = Modifier.padding(all = 10.dp)
  ) { innerPadding ->
    Column(modifier = Modifier.padding(innerPadding)) {
      vm.selectedRestaurant?.let {
        RestaurantDetail(
          image = it.image,
          name = it.name,
          options = it.options,
          time = getDiscountTiming(it.openingTime, it.closingTime),
          address = it.address,
          deals = it.deals,
        )
      }
    }
  }
}

@Composable
fun RestaurantDetail(
  image: String,
  name: String,
  options: ImmutableList<String>,
  time: String,
  address: String,
  deals: ImmutableList<Restaurant.Deal>
) {
  Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
    AsyncImage(
      model = ImageRequest.Builder(LocalContext.current)
        .data(image)
        .crossfade(true)
        .build(),
      error = painterResource(R.drawable.placeholder),
      placeholder = painterResource(R.drawable.placeholder),
      contentDescription = stringResource(R.string.distance),
      contentScale = ContentScale.Crop,
      modifier = Modifier
        .fillMaxWidth()
        .height(260.dp),
    )
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth(0.8f)
    ) {
      Icon(
        imageVector = Icons.Default.Menu,
        contentDescription = "",
      )
      Icon(
        imageVector = Icons.Default.Call,
        contentDescription = "",
      )
      Icon(
        imageVector = Icons.Default.LocationOn,
        contentDescription = "",
      )
      Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = "",
      )
    }
    Divider(thickness = Dp.Hairline)
    Text(
      text = name,
      style = MaterialTheme.typography.titleLarge,
      modifier = Modifier.padding(top = 20.dp)
    )

    OptionsBuilder(
      options,
      modifier = Modifier.padding(bottom = 20.dp)
    )

    Column(modifier = Modifier.padding(horizontal = 40.dp)) {
      Divider(thickness = Dp.Hairline)
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.DateRange,
          contentDescription = "",
        )
        Text(
          text = time,
          style = MaterialTheme.typography.bodyMedium,
          modifier = Modifier.padding(start = 20.dp)
        )
      }
      Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.LocationOn,
          contentDescription = "",
        )
        Text(
          text = address,
          style = MaterialTheme.typography.bodyMedium,
          modifier = Modifier.padding(start = 20.dp)
        )
      }
      Divider(thickness = Dp.Hairline, modifier = Modifier.padding(top = 10.dp))
      LazyColumn(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        items(deals) {
          DealSection(it)
        }
      }
    }

  }
}

@Composable
fun DealSection(deal: Restaurant.Deal) {
  Row {
    Column(modifier = Modifier.fillMaxWidth(0.6f)) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          painterResource(R.drawable.lightning_svgrepo_com), contentDescription = "",
          modifier = Modifier.size(24.dp),
          tint = MaterialTheme.colorScheme.scrim
        )
        Text(
          text = deal.discountPercent,
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.tertiary
        )
      }
      Text(
        text = getDiscountTiming(deal.startTime, deal.endTime),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onPrimary
      )
      Text(
        text = stringResource(R.string.deals_left, deal.quantityLeft),
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onPrimary
      )
    }
    Column(
      modifier = Modifier.fillMaxSize(),
      verticalArrangement = Arrangement.Center
    ) {
      OutlinedButton(
        onClick = {},
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary),
        colors = ButtonDefaults.outlinedButtonColors().copy(
          contentColor = MaterialTheme.colorScheme.tertiary
        )
      ) {
        Text(text = stringResource(R.string.redeem))
      }
    }
  }

}

@Composable
@Preview(showBackground = true)
fun RestaurantDetailPreview() {
  EatClubTheme {
    Column {
      val restaurant = Restaurant(
        id = "asd",
        name = "The Bar",
        distance = "0.5km",
        cuisine = "Italian",
        address = "Spensor st",
        options = persistentListOf("Dine in", "Take away"),
        openingTime = "5am",
        closingTime = "10pm",
        image = "https://dinnerdeal.backendless.com/api/e14e5098-2393-6d4a-ff80-f5564e042100/v1/files/restaurant_images/DEA567C5-F64C-3C03-FF00-E3B24909BE00_image_0_1520389372647.jpg",
        deals = persistentListOf(
          Restaurant.Deal("20", "10am", "10pm", "5"),
          Restaurant.Deal("10", "10am", "10pm", "5")
        )
      )
      RestaurantDetail(
        image = restaurant.image,
        name = restaurant.name,
        options = restaurant.options,
        time = getDiscountTiming(restaurant.openingTime, restaurant.closingTime),
        address = restaurant.address,
        deals = restaurant.deals,
      )
    }
  }

}