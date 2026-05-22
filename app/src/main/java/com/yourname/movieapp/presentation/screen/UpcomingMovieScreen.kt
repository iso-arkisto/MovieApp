package com.yourname.movieapp.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.yourname.movieapp.presentation.MovieListState
import com.yourname.movieapp.presentation.MovieListUiEvent
import com.yourname.movieapp.presentation.components.MovieItem
import com.yourname.movieapp.utils.Category

@Composable
fun UpcomingMovieScreen(
    state: MovieListState,
    navHostController: NavHostController,
    onEvent: (MovieListUiEvent) -> Unit
) {
    if(state.upcomingMovieList.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items(state.upcomingMovieList.size) { index ->
                MovieItem(
                    item = state.upcomingMovieList[index],
                    navHostController = navHostController
                )
                Spacer(modifier = Modifier.height(16.dp))

                if(index >= state.upcomingMovieList.size - 1 && !state.isLoading) {
                    onEvent(MovieListUiEvent.Paginate(Category.UPCOMING))
                }
            }
        }
    }
}