package com.yourname.movieapp.presentation.screen.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.yourname.movieapp.data.remote.MovieApi
import com.yourname.movieapp.utils.RatingBar

@Composable
fun DetailsScreen() {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val state = viewModel.detailsState.collectAsState().value

    val backdropPath = state.movie?.backdrop_path.orEmpty()
    val posterPath = state.movie?.poster_path.orEmpty()

    val backdropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(
             MovieApi.IMG_BASE_URL+backdropPath.removePrefix("/")
            )
            .size(
                Size.ORIGINAL
            )
            .build()
    ).state

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(
                MovieApi.IMG_BASE_URL+posterPath.removePrefix("/")
            )
            .size(
                Size.ORIGINAL
            )
            .build()
    ).state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) {
            when {
                backdropPath.isEmpty() || backdropImageState is AsyncImagePainter.State.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ImageNotSupported,
                            contentDescription = "Image not supported",
                            modifier = Modifier.size(70.dp)
                        )
                    }
                }
                backdropImageState is AsyncImagePainter.State.Success -> {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = backdropImageState.painter,
                        contentDescription = "Backdrop",
                        contentScale = ContentScale.Crop
                    )
                }
                else -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primaryContainer),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(240.dp)
            ) {
                when {
                    posterPath.isEmpty() || posterImageState is AsyncImagePainter.State.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ImageNotSupported,
                                contentDescription = "Image not supported",
                                modifier = Modifier.size(70.dp)
                            )
                        }
                    }
                    posterImageState is AsyncImagePainter.State.Success -> {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            painter = posterImageState.painter,
                            contentDescription = "Poster",
                            contentScale = ContentScale.Crop
                        )
                    }
                    else -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.primaryContainer)
                        )
                    }
                }
            }

            state.movie?.let { movie ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = movie.title,
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 19.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        RatingBar(
                            starsModifier = Modifier.size(18.dp),
                            rating = movie.vote_average/2
                        )

                        Text(
                            text = movie.vote_average.toString().take(3),
                            modifier = Modifier.padding(start = 4.dp),
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = movie.original_language,
                        modifier = Modifier.padding(16.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = movie.release_date,
                        modifier = Modifier.padding(16.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = movie.vote_count.toString(),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Overview",
            modifier = Modifier.padding(start = 16.dp),
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))

        state.movie?.let { movie ->
            Text(
                text = movie.overview,
                modifier = Modifier.padding(start = 16.dp),
                fontSize = 19.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

    }


}