package com.me.weather.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.me.weather.R
import com.me.weather.domain.model.Forecast
import com.me.weather.presentation.ui.preview.ListForecastPreviewProvider
import com.me.weather.presentation.ui.preview.ThemePreview
import com.me.weather.presentation.ui.theme.WeatherTheme
import com.me.weather.presentation.utils.extensions.toPrettyDateShort
import com.me.weather.presentation.utils.extensions.toUrlImgOpenWeather4x

@Composable
fun ForecastSection(
    modifier: Modifier = Modifier,
    forecasts: List<Forecast>,
) {
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
            Text(
                stringResource(R.string.forecast).replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
            )
            HorizontalDivider()

//            TODO: add empty state
            forecasts.forEachIndexed { index, item ->
                Item(
                    item = item,
                    lastIndex = forecasts.lastIndex,
                    index = index,
                )
            }
        }
    }
}

@Composable
private fun ColumnScope.Item(
    item: Forecast,
    lastIndex: Int,
    index: Int,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Text(
                text = item.dt.toPrettyDateShort(),
                fontWeight = FontWeight.SemiBold,
            )

            Text(
                text = item.description,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.alpha(0.5f)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            AsyncImage(
                model = item.icon.toUrlImgOpenWeather4x(),
                contentDescription = "${item.description}, icon",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(50),
                    )
            )

            Text(
                text = "${item.temp}°",
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }

    if (index < lastIndex) {
        HorizontalDivider(modifier = Modifier.alpha(0.4f))
    }
}

@ThemePreview
@Composable
private fun Preview(
    @PreviewParameter(ListForecastPreviewProvider::class) forecasts: List<Forecast>,
) {
    WeatherTheme {
        Surface {
            ForecastSection(
                forecasts = forecasts,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
