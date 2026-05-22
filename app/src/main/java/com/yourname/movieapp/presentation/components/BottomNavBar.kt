package com.yourname.movieapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.yourname.movieapp.presentation.MovieListUiEvent
import com.yourname.movieapp.utils.Screen

@Composable
fun BottomNavBar(
    navController: NavHostController,
    onEvent: (MovieListUiEvent) -> Unit
) {
    val items = listOf<BottomItem>(
        BottomItem("Popular", Icons.Rounded.Movie),
        BottomItem("Upcoming", Icons.Rounded.Upcoming)
    )

    val selectedItem = rememberSaveable(items) { mutableIntStateOf(0) }

    NavigationBar() {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.inverseSurface)
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = selectedItem.intValue == index,
                    onClick = {
                        selectedItem.intValue = index
                        onEvent(MovieListUiEvent.Navigate)
                        when(selectedItem.intValue) {
                            0 -> {
                                navController.popBackStack()
                                navController.navigate(Screen.PopularMovies)
                            }
                            1 -> {
                                navController.popBackStack()
                                navController.navigate(Screen.UpcomingMovies)
                            }
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}

data class BottomItem(
    val title: String,
    val icon: ImageVector
)