import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import au.com.eatclub.R
import au.com.eatclub.ui.screens.restaurant.list.debugPlaceholder
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import kotlinx.collections.immutable.ImmutableList

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
        Text(
          stringResource(R.string.discount_terms_anytime),//todo
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