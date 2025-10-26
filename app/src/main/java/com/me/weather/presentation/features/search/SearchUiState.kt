package com.me.weather.presentation.features.search

import com.me.weather.domain.model.Record
import com.me.weather.domain.model.Weather
import com.me.weather.presentation.utils.RequestState

data class SearchUiState(
    val query: String = "",
    val weather: Weather? = null,
    val weatherRequestState: RequestState<Weather> = RequestState.Idle,
    val records: List<Record> = emptyList(),
)
