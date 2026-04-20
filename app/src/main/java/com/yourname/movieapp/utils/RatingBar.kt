package com.yourname.movieapp.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    starsModifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating).toInt())
    val halfStars = rating.rem(1) != 0.0

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                modifier = starsModifier,
                tint = starsColor,
                contentDescription = null,
                imageVector = Icons.Rounded.Star
            )
        }
        if(halfStars) {
            Icon(
                modifier = starsModifier,
                tint = starsColor,
                contentDescription = null,
                imageVector = Icons.Rounded.StarHalf
            )
        }
        repeat(unfilledStars) {
            Icon(
                modifier = starsModifier,
                tint = starsColor,
                contentDescription = null,
                imageVector = Icons.Rounded.StarOutline
            )
        }
    }


}

@Composable
fun RatingItem(
    rating: Double,
    label: String,
    starsColor: Color = Color.Yellow,
    starSize: Int = 24
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.height(4.dp))
        RatingBar(
            rating = rating,
            starsColor = starsColor,
            starsModifier = Modifier.size(starSize.dp)
        )
        Text(
            text = "Rating: $rating",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )
    }
}

@Preview(name = "Different ratings", showBackground = true)
@Composable
fun RatingBarPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Rating",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )


                RatingItem(rating = 5.0, label = "Maximum rating")
                RatingItem(rating = 4.5, label = "Half star")
                RatingItem(rating = 3.7, label = "3.7 stars (floor)")
                RatingItem(rating = 2.0, label = "Whole amount of stars")
                RatingItem(rating = 0.0, label = "No rating")
                RatingItem(rating = 1.2, label = "1.2 stars")

                Spacer(modifier = Modifier.height(24.dp))


                Text(
                    text = "Different colors",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                RatingItem(
                    rating = 4.0,
                    label = "Gold stars",
                    starsColor = Color(0xFFFFD700)
                )

                RatingItem(
                    rating = 3.5,
                    label = "Blue stars",
                    starsColor = Color.Blue
                )

                RatingItem(
                    rating = 2.8,
                    label = "Red stars",
                    starsColor = Color.Red
                )


                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Small stars",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Movie 1",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        RatingBar(
                            rating = 4.2,
                            starsModifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Movie 2",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        RatingBar(
                            rating = 3.7,
                            starsModifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PrevStars() {
    RatingBar(
        rating = 0.0,
        stars= 5
    )
}