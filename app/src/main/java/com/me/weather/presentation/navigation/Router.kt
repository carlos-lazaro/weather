package com.me.weather.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Router(val name: String) {
    @Serializable
    data object Home : Router("Home")

    @Serializable
    data object Search : Router("Search")
}
