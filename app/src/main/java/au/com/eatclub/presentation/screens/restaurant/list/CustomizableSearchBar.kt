package au.com.eatclub.presentation.screens.restaurant.list

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun CustomizableSearchBar(
  searchText: String,
  onSearchTextChanged: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  OutlinedTextField(
    value = searchText,
    onValueChange = onSearchTextChanged,
    label = { Text("Search...") },
    modifier = modifier
  )
}