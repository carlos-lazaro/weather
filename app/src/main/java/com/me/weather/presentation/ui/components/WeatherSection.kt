package com.me.weather.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.me.weather.R
import com.me.weather.domain.model.Favorite
import com.me.weather.domain.model.Weather
import com.me.weather.domain.model.toFavorite
import com.me.weather.presentation.ui.preview.SingleWeatherPreviewProvider
import com.me.weather.presentation.ui.preview.ThemePreview
import com.me.weather.presentation.ui.theme.WeatherTheme
import com.me.weather.presentation.utils.extensions.toPrettyDate

@Composable
fun WeatherSection(
    modifier: Modifier = Modifier,
    weather: Weather,
    favoritesIds: List<Int> = emptyList(),
    onAddFavorite: (Weather) -> Unit = {},
    onDeleteFavorite: (Favorite) -> Unit = {},
) {
    val isFavorite by remember(weather.id, favoritesIds) {
        mutableStateOf(
            favoritesIds.contains(
                weather.id,
            )
        )
    }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    Icons.Default.LocationOn,
                    contentDescription = stringResource(R.string.location),
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = buildAnnotatedString {
                        append(weather.name)
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                            )
                        ) {
                            append(" (${weather.country})\n")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = MaterialTheme.typography.labelSmall.fontSize,
                            )
                        ) {
                            append("${weather.dt.toPrettyDate()}")
                        }
                    },
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        if (isFavorite)
                            onDeleteFavorite(weather.toFavorite())
                        else
                            onAddFavorite(weather)
                    }
                ) {
                    Icon(
                        Icons.Default.Star,
                        tint = if (isFavorite) MaterialTheme.colorScheme.inversePrimary else LocalContentColor.current,
                        contentDescription = stringResource(R.string.delete_favorite),
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "${weather.temp.toInt()}°",
                style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = buildAnnotatedString {
                    append(weather.main)
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontStyle = FontStyle.Italic,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize,
                        )
                    ) {
                        append(", ${weather.description}")
                    }
                },
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = buildAnnotatedString {
                    append("\u2191 ${weather.tempMax.toInt()}° / \u2193 ${weather.tempMin.toInt()}° \n")
                    append("${stringResource(R.string.feels_like)} ${weather.feelsLike.toInt()}°")

                },
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@ThemePreview
@Composable
private fun Preview(
    @PreviewParameter(SingleWeatherPreviewProvider::class) weather: Weather,
) {
    WeatherTheme {
        Surface {
            WeatherSection(
                weather = weather,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
