@file:OptIn(ExperimentalMaterial3Api::class)

package com.me.weather.presentation.features.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.me.weather.R
import com.me.weather.domain.model.Record
import com.me.weather.domain.model.Weather
import com.me.weather.presentation.ui.components.ChipElement
import com.me.weather.presentation.ui.components.SearchTextField
import com.me.weather.presentation.ui.components.WeatherMapCard
import com.me.weather.presentation.ui.preview.ThemePreview
import com.me.weather.presentation.ui.theme.WeatherTheme
import com.me.weather.presentation.utils.ObserveAsEvents
import com.me.weather.presentation.utils.RequestState

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel<SearchViewModel>(),
    innerPadding: PaddingValues,
    onBack: () -> Unit = {},
) {
    val context = LocalContext.current
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEvents(searchViewModel.events) { event ->
        when (event) {
            is SearchScreenEvent.Error -> {
                Toast.makeText(
                    context,
                    event.message.asString(context),
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    Content(
        uiState = uiState,
        innerPadding = innerPadding,
        onAction = { action ->
            searchViewModel.onAction(action)
        },
        onBack = onBack,
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    uiState: SearchUiState,
    innerPadding: PaddingValues,
    onAction: (SearchScreenAction) -> Unit = {},
    onBack: () -> Unit = {},
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var isMapReady by remember { mutableStateOf(false) }
    var isSheetOpen by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.weatherRequestState) {
        when (val state = uiState.weatherRequestState) {
            is RequestState.Error -> {}
            RequestState.Idle -> {}
            RequestState.Loading -> {}
            is RequestState.Success<Weather> -> {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(
                        LatLng(state.data.lat, state.data.lon),
                        uiState.cameraZoom,
                    ),
                    durationMs = uiState.cameraDurations,
                )
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues = innerPadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.weight(1f)
            ) {
                SearchTextField(
                    value = uiState.query,
                    onValueChange = { onAction(SearchScreenAction.OnQueryChange(it)) },
                    requestState = uiState.weatherRequestState,
                    onSearch = {
                        onAction(SearchScreenAction.OnSearch(uiState.query))
                    },
                )
            }

            IconButton(
                enabled = uiState.weatherRequestState !is RequestState.Loading,
                onClick = {
                    if (uiState.records.isNotEmpty())
                        isSheetOpen = true
                    else
                        Toast.makeText(
                            context,
                            context.getString(R.string.you_didn_t_make_a_search_yet),
                            Toast.LENGTH_SHORT,
                        ).show()
                },
            ) {
                Icon(
                    painterResource(R.drawable.outline_overview_24),
                    stringResource(R.string.location),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
        ) {
            GoogleMap(
                uiSettings = MapUiSettings(
                    zoomControlsEnabled = false,
                ),
                cameraPositionState = cameraPositionState,
                onMapLoaded = {
                    isMapReady = true
                },
                modifier = Modifier
                    .fillMaxSize()
            ) {
                when (val state = uiState.weatherRequestState) {
                    is RequestState.Error -> {}
                    RequestState.Idle -> {}
                    RequestState.Loading -> {}
                    is RequestState.Success<Weather> -> {
                        Marker(
                            state = rememberUpdatedMarkerState(
                                position = LatLng(state.data.lat, state.data.lon),
                            ),
                        )
                    }
                }
            }

            if (!isMapReady)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(32.dp)
                    )
                }

            uiState
                .weather
                ?.let { weather ->
                    WeatherMapCard(
                        weather = weather,
                        modifier = Modifier
                            .padding(32.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        onAction(SearchScreenAction.SetDefault(weather = weather))
                        onBack()
                    }
                }
        }

        if (isSheetOpen) {
            ModalBottomSheet(
                onDismissRequest = { isSheetOpen = false },
                sheetState = sheetState,
            ) {
                SheetContent(
                    records = uiState.records,
                    onSelect = { record ->
                        onAction(SearchScreenAction.OnQueryChange(record.name))
                        onAction(SearchScreenAction.OnSearch(record.name))
                        isSheetOpen = false
                    },
                    onClearHistory = {
                        onAction(SearchScreenAction.OnClearHistory)
                        isSheetOpen = false
                    },
                )
            }
        }
    }
}

@Composable
private fun SheetContent(
    records: List<Record>,
    onSelect: (Record) -> Unit,
    onClearHistory: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(24.dp)
    ) {
        Text(
            text = stringResource(R.string.recent).replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleMedium,
        )

        Spacer(Modifier.padding(8.dp))

        Box(
            modifier = Modifier
                .weight(1f, fill = false)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp)
        ) {
            FlowRow(
                maxItemsInEachRow = Int.MAX_VALUE,
                modifier = Modifier.fillMaxWidth()
            ) {
                records.forEach { record ->
                    ChipElement(
                        modifier = Modifier.padding(4.dp),
                        record = record,
                        onClick = { onSelect(it) },
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = { onClearHistory() },
                modifier = Modifier
            ) {
                Text(
                    stringResource(R.string.clear_history),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                )
            }
        }
    }
}

@ThemePreview
@Composable
private fun Preview() {
    WeatherTheme {
        Surface {
            Content(
                uiState = SearchUiState(),
                innerPadding = PaddingValues(0.dp),
            )
        }
    }
}
