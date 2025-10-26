package com.me.weather.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.me.weather.data.local.entities.ForecastEntity

data class ForecastItemDto(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("main")
    val main: ForecastMainDto,
    @SerializedName("weather")
    val weather: List<WeatherDto>,
    @SerializedName("clouds")
    val clouds: CloudsDto,
    @SerializedName("wind")
    val wind: ForecastWindDto,
    @SerializedName("visibility")
    val visibility: Int,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("sys")
    val sys: ForecastSysDto,
    @SerializedName("dt_txt")
    val dtTxt: String,
)


fun ForecastItemDto.toEntity(cityId: Int): ForecastEntity {
    return ForecastEntity(
        dt = dt,
        temp = main.temp,
        feelsLike = main.feelsLike,
        tempMin = main.tempMin,
        tempMax = main.tempMax,
        pressure = main.pressure,
        humidity = main.humidity,
        main = weather.firstOrNull()?.main.orEmpty(),
        description = weather.firstOrNull()?.description.orEmpty(),
        icon = weather.firstOrNull()?.icon.orEmpty(),
        clouds = clouds.all,
        windSpeed = wind.speed,
        windDeg = wind.deg,
        visibility = visibility,
        pop = pop,
        partOfDay = sys.pod,
        dateText = dtTxt,
        cityId = cityId,
    )
}
