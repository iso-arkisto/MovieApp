package com.yourname.movieapp.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.yourname.movieapp.presentation.MovieListState
import com.yourname.movieapp.presentation.MovieListViewModel
import com.yourname.movieapp.presentation.components.BottomNavBar
import com.yourname.movieapp.utils.Screen
import androidx.navigation.compose.composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController
) {
    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    val state = movieListViewModel.movieListState.collectAsState().value

    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                bottomNavController,
                onEvent = movieListViewModel::onEvent
            )
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if(state.isCurrentPopularScreen) "Popular movies" else "Upcoming movies",
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier
                    .shadow(2.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
                bottomNavController,
                Screen.PopularMovies.route
            ) {
                composable(Screen.PopularMovies.route) {
                    PopularMovieScreen(
                        state = state,
                        navHostController = navHostController,
                        onEvent = movieListViewModel::onEvent
                    )
                }
                composable(Screen.UpcomingMovies.route) {
                    UpcomingMovieScreen(
                        state = state,
                        navHostController = navHostController,
                        onEvent = movieListViewModel::onEvent
                    )
                }
            }
        }
    }
}