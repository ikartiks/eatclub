package au.com.eatclub.presentation.screens.restaurant.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import au.com.eatclub.R
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce

@OptIn(FlowPreview::class)
@Composable
fun TopBar(
  onSearch: (String) -> Unit,
  modifier: Modifier = Modifier,
  showSearch: Boolean = true,
) {
  Column(modifier) {
    Row (horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier
        .fillMaxWidth()){
      Icon(
        Icons.Default.AccountBox,
        contentDescription = stringResource(R.string.account),
        tint = MaterialTheme.colorScheme.scrim,
        modifier = Modifier.size(48.dp)
      )
      Image(
        painterResource(R.drawable.logo),
        contentDescription = stringResource(R.string.app_name),
        modifier = Modifier.size(48.dp))
      Icon(
        Icons.Default.Settings,
        contentDescription = stringResource(R.string.account),
        tint = MaterialTheme.colorScheme.scrim,
        modifier = Modifier.size(48.dp)
      )
    }
    if (showSearch) {
      var query by rememberSaveable { mutableStateOf("") }
      LaunchedEffect(query) {
        snapshotFlow { query }
          .debounce(250L)
          .collectLatest { search ->
            onSearch(search)
          }
      }

      CustomizableSearchBar(
        searchText = query,
        onSearchTextChanged = { query = it },
        modifier = Modifier.fillMaxWidth()
      )
    }
  }
}