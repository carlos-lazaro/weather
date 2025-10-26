package com.me.weather.presentation.features.search

import com.me.weather.domain.model.Weather

sealed interface SearchScreenAction {
    data class OnQueryChange(val query: String) : SearchScreenAction
    data class OnSearch(val query: String) : SearchScreenAction
    data class SetDefault(val weather: Weather) : SearchScreenAction
}
