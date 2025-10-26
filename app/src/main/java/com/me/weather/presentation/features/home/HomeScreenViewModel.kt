@file:OptIn(ExperimentalCoroutinesApi::class)

package com.me.weather.presentation.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.me.weather.domain.model.AppDispatchers
import com.me.weather.domain.model.Dispatcher
import com.me.weather.domain.model.ResponseResource
import com.me.weather.domain.model.Weather
import com.me.weather.domain.model.toFavorite
import com.me.weather.domain.repository.FavoriteRepository
import com.me.weather.domain.repository.ForecastRepository
import com.me.weather.domain.repository.UserPreferencesRepository
import com.me.weather.domain.repository.WeatherRepository
import com.me.weather.domain.use_case.LoadDataUseCase
import com.me.weather.presentation.utils.RequestState
import com.me.weather.presentation.utils.RequestState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    @param:Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val loadDataUseCase: LoadDataUseCase,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val weatherRepository: WeatherRepository,
    private val forecastRepository: ForecastRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _uiState.value,
    )

    init {
        viewModelScope.launch(ioDispatcher) {
            userPreferencesRepository
                .observeWeatherId()
                .collectLatest { id ->
                    id?.let { id ->
                        _uiState.update {
                            it.copy(currentWeatherId = id)
                        }
                    }
                }
        }
        viewModelScope.launch(ioDispatcher) {
            uiState
                .map { it.currentWeatherId }
                .distinctUntilChanged()
                .flatMapLatest { id ->
                    weatherRepository.observeWeatherById(id)
                }
                .collectLatest { weather ->
                    weather?.let { weather ->
                        _uiState.update {
                            it.copy(weather = weather)
                        }
                    }
                }
        }
        viewModelScope.launch(ioDispatcher) {
            uiState
                .map { it.currentWeatherId }
                .distinctUntilChanged()
                .flatMapLatest { id ->
                    forecastRepository.observeForecastByCityId(id)
                }
                .collectLatest { forecasts ->
                    forecasts
                        .takeIf { it.isNotEmpty() }
                        ?.let { forecasts ->
                            _uiState.update {
                                it.copy(forecasts = forecasts)
                            }
                        }
                }
        }
        viewModelScope.launch(ioDispatcher) {
            favoriteRepository
                .observeAll()
                .collectLatest { favorites ->
                    _uiState.update {
                        it.copy(
                            favorites = favorites,
                            favoritesIds = favorites.map { item -> item.id },
                        )
                    }
                }
        }
    }

    fun onAction(action: HomeScreenAction) {
        when (action) {
            is HomeScreenAction.OnAddFavorite -> {
                viewModelScope.launch(ioDispatcher) {
                    favoriteRepository.add(action.weather.toFavorite())
                }
            }

            is HomeScreenAction.OnDeleteFavorite -> {
                viewModelScope.launch(ioDispatcher) {
                    favoriteRepository.delete(action.favorite)
                }
            }

            HomeScreenAction.OnRefreshWeather -> {
                _uiState
                    .value
                    .weather?.let { weather ->
                        _uiState.update { it.copy(weatherRequestState = RequestState.Loading) }

                        viewModelScope.launch(ioDispatcher) {
                            when (val res = loadDataUseCase(query = weather.name)) {
                                is ResponseResource.Error -> {
                                    _uiState.update { it.copy(weatherRequestState = RequestState.Idle) }
                                }

                                is ResponseResource.Success<Weather> -> {
                                    _uiState.update {
                                        it.copy(
                                            weatherRequestState = Success(data = res.data),
                                            weather = res.data,
                                        )
                                    }
                                }
                            }
                        }
                    }
            }
        }
    }
}
