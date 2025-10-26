package com.me.weather.presentation.features.home

import com.me.weather.domain.model.Favorite
import com.me.weather.domain.model.Weather

sealed interface HomeScreenAction {
    data class OnDeleteFavorite(val favorite: Favorite) : HomeScreenAction
    data class OnAddFavorite(val weather: Weather) : HomeScreenAction
    data object OnRefreshWeather : HomeScreenAction
}
