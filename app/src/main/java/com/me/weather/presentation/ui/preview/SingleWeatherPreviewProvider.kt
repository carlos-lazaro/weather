package com.me.weather.presentation.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.me.weather.domain.model.Weather

class SingleWeatherPreviewProvider : PreviewParameterProvider<Weather> {
    override val values = sequenceOf(
        Weather(
            id = 2,
            main = "Clouds",
            description = "Partly cloudy",
            icon = "02d",
            name = "Medellín",
            country = "CO",
            lat = 6.2442,
            lon = -75.5812,
            temp = 24.5,
            feelsLike = 25.0,
            tempMin = 22.0,
            tempMax = 26.0,
            pressure = 1015,
            humidity = 70,
            wind = 27.53,
            visibility = 9777,
            dt = 1761337569,
            sunrise = 1761286551,
            sunset = 1761325036,
        ),
    )
}
