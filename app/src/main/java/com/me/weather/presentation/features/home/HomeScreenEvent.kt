package com.me.weather.presentation.features.home

import com.me.weather.presentation.utils.UiText

sealed interface HomeScreenEvent {
    data class Error(val message: UiText) : HomeScreenEvent
}
