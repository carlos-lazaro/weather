package com.me.weather.presentation.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.me.weather.R
import com.me.weather.domain.model.Weather
import com.me.weather.presentation.ui.preview.SingleWeatherPreviewProvider
import com.me.weather.presentation.ui.preview.ThemePreview
import com.me.weather.presentation.ui.theme.WeatherTheme
import com.me.weather.presentation.utils.extensions.roundTo
import com.me.weather.presentation.utils.extensions.toKilometers
import com.me.weather.presentation.utils.extensions.toPrettyDateSunsetSunrise

@Composable
fun WeatherIndicator(
    modifier: Modifier = Modifier,
    weather: Weather,
    gap: Dp = 16.dp,
    styleText: TextStyle = MaterialTheme.typography.bodySmall,
) {
    val context = LocalContext.current
    val scroll = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(scroll)
            .padding(12.dp)
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        ItemIndicator {
            Icon(
                painterResource(R.drawable.outline_humidity_percentage_24),
                context.getString(R.string.humidity),
            )

            Text(
                "${weather.humidity}%",
                style = styleText,
            )
        }

        Spacer(modifier = Modifier.width(gap))

        ItemIndicator {
            Icon(
                painterResource(R.drawable.outline_air_24),
                context.getString(R.string.humidity),
            )

            Text(
                "${weather.wind} m/s",
                style = styleText,
            )
        }

        Spacer(modifier = Modifier.width(gap))

        ItemIndicator {
            Icon(
                Icons.Default.RemoveRedEye,
                context.getString(R.string.humidity),
            )

            Text(
                "${weather.visibility.toKilometers().roundTo(2)} km",
                style = styleText,
            )
        }

        Spacer(modifier = Modifier.width(gap))

        ItemIndicator {
            Icon(
                painterResource(R.drawable.sunrise),
                stringResource(R.string.sunrise)
            )

            Text(
                weather.sunrise.toPrettyDateSunsetSunrise(),
                style = styleText,
            )
        }

        Spacer(modifier = Modifier.width(gap))

        ItemIndicator {
            Icon(
                painterResource(R.drawable.sunset),
                stringResource(R.string.sunset),
            )

            Text(
                weather.sunset.toPrettyDateSunsetSunrise(),
                style = styleText,
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
    }
}

@Composable
private fun ItemIndicator(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = modifier
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
    }
}

@ThemePreview
@Composable
private fun Preview(
    @PreviewParameter(SingleWeatherPreviewProvider::class) weather: Weather,
) {
    WeatherTheme {
        Surface {
            WeatherIndicator(weather = weather)
        }
    }
}
