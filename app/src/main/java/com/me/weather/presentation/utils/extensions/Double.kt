package com.me.weather.presentation.utils.extensions

import kotlin.math.pow
import kotlin.math.roundToInt

fun Double.roundTo(decimalPlaces: Int): Double {
    val factor = 10.0.pow(decimalPlaces.toDouble())
    return (this * factor).roundToInt() / factor
}
