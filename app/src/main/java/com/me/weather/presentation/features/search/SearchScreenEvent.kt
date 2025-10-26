package com.me.weather.presentation.features.search

import com.me.weather.presentation.utils.UiText

sealed interface SearchScreenEvent {
    data class Error(val message: UiText) : SearchScreenEvent
}
