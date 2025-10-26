package com.me.weather.presentation.features.home

import com.me.weather.domain.model.Favorite
import com.me.weather.domain.model.Forecast
import com.me.weather.domain.model.Record
import com.me.weather.domain.model.Weather
import com.me.weather.presentation.utils.RequestState

data class HomeUiState(
    val currentWeatherId: Int = 0,
    val weather: Weather? = null,
    val weatherRequestState: RequestState<Weather> = RequestState.Idle,
    val forecasts: List<Forecast> = emptyList(),
    val favorites: List<Favorite> = emptyList(),
    val favoritesIds: List<Int> = emptyList(),
    val records: List<Record> = emptyList(),
)
