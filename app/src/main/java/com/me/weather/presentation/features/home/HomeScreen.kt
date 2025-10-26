package com.me.weather.presentation.features.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.me.weather.R
import com.me.weather.presentation.ui.components.FavoriteSection
import com.me.weather.presentation.ui.components.ForecastSection
import com.me.weather.presentation.ui.components.WeatherIndicator
import com.me.weather.presentation.ui.components.WeatherSection
import com.me.weather.presentation.ui.preview.ThemePreview
import com.me.weather.presentation.ui.theme.WeatherTheme
import com.me.weather.presentation.utils.RequestState

@Composable
fun HomeScreen(
    homeViewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>(),
    innerPadding: PaddingValues,
    goToSearch: () -> Unit = {},
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    Content(
        uiState = uiState,
        innerPadding = innerPadding,
        onAction = { action ->
            homeViewModel.onAction(action)
        },
        goToSearch = goToSearch,
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    innerPadding: PaddingValues,
    onAction: (HomeScreenAction) -> Unit = {},
    goToSearch: () -> Unit = {},
) {
    val state = rememberPullToRefreshState()
    val scrollState = rememberScrollState()

    PullToRefreshBox(
        isRefreshing = uiState.weatherRequestState is RequestState.Loading,
        onRefresh = {
            onAction(HomeScreenAction.OnRefreshWeather)
        },
        state = state,
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = innerPadding)
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.size(16.dp))

            uiState.weather?.let { weather ->
                WeatherSection(
                    weather = weather,
                    favoritesIds = uiState.favoritesIds,
                    onAddFavorite = {
                        onAction(HomeScreenAction.OnAddFavorite(it))
                    },
                    onDeleteFavorite = {
                        onAction(HomeScreenAction.OnDeleteFavorite(it))
                    },
                )
                WeatherIndicator(
                    weather = weather,
                )

                uiState
                    .forecasts
                    .takeIf { it.isNotEmpty() }
                    ?.let { forecast ->
                        Spacer(modifier = Modifier.size(16.dp))

                        ForecastSection(forecasts = forecast)
                    }
            } ?: EmptyState()

            Spacer(modifier = Modifier.size(16.dp))

            FavoriteSection(
                favorites = uiState.favorites,
                onDeleteFavorite = {
                    onAction(HomeScreenAction.OnDeleteFavorite(it))
                },
            )

            Spacer(modifier = Modifier.size(64.dp))
        }

        FloatingActionButton(
            onClick = { goToSearch() },
            modifier = Modifier
                .padding(innerPadding)
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Search, stringResource(R.string.search))
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Text(
        stringResource(R.string.empty_state_search_cities),
        style = MaterialTheme.typography.bodyMedium,
        textAlign = TextAlign.Center,
        modifier = modifier
            .padding(vertical = 12.dp)
            .alpha(0.5f)
    )
}

@ThemePreview
@Composable
private fun Preview() {
    WeatherTheme {
        Content(
            uiState = HomeUiState(),
            innerPadding = PaddingValues(0.dp),
        )
    }
}
