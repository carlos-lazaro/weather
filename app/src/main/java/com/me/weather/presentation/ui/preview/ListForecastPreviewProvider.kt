package com.me.weather.presentation.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.me.weather.domain.model.Forecast

class ListForecastPreviewProvider : PreviewParameterProvider<List<Forecast>> {
    private val base = Forecast(
        id = 1,
        dt = 1698451200L,
        temp = 22.5,
        feelsLike = 23.0,
        tempMin = 21.0,
        tempMax = 25.0,
        pressure = 1013,
        humidity = 68,
        main = "Clouds",
        description = "broken clouds",
        icon = "04d",
        clouds = 75,
        windSpeed = 3.6,
        windDeg = 180,
        visibility = 10000,
        pop = 0.15,
        partOfDay = "d",
        dateText = "2025-10-27 12:00:00",
        cityId = 123,
        createdAt = System.currentTimeMillis(),
    )

    override val values = sequenceOf(
        List(5) {
            base.copy(
                id = base.id + it,
                dt = base.dt + it * 10800L,
                temp = base.temp + it,
            )
        },
    )
}
