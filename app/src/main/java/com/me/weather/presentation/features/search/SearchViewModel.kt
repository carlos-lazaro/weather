package com.me.weather.presentation.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.me.weather.R
import com.me.weather.domain.model.AppDispatchers
import com.me.weather.domain.model.Dispatcher
import com.me.weather.domain.model.toRecord
import com.me.weather.domain.repository.RecordRepository
import com.me.weather.domain.repository.UserPreferencesRepository
import com.me.weather.domain.use_case.LoadDataUseCase
import com.me.weather.domain.util.DataError
import com.me.weather.domain.util.onError
import com.me.weather.domain.util.onSuccess
import com.me.weather.presentation.features.search.SearchScreenEvent.Error
import com.me.weather.presentation.utils.RequestState
import com.me.weather.presentation.utils.RequestState.Success
import com.me.weather.presentation.utils.UiText
import com.me.weather.presentation.utils.asUiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @param:Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val loadDataUseCase: LoadDataUseCase,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val recordRepository: RecordRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = _uiState.value,
    )

    private val eventChannel = Channel<SearchScreenEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            recordRepository
                .observeAll()
                .collectLatest { records ->
                    _uiState.update {
                        it.copy(records = records)
                    }
                }
        }
    }

    fun onAction(action: SearchScreenAction) {
        when (action) {
            is SearchScreenAction.OnQueryChange -> {
                _uiState.update { it.copy(query = action.query) }
            }

            is SearchScreenAction.OnSearch -> {
                action
                    .query
                    .takeIf { it.isNotBlank() }
                    ?.let {
                        _uiState.update { it.copy(weatherRequestState = RequestState.Loading) }

                        viewModelScope.launch(ioDispatcher) {
                            loadDataUseCase(query = action.query)
                                .onSuccess { data ->
                                    recordRepository.add(data.toRecord())

                                    _uiState.update {
                                        it.copy(
                                            weatherRequestState = Success(data = data),
                                            weather = data,
                                        )
                                    }
                                }
                                .onError { data ->
                                    _uiState.update { it.copy(weatherRequestState = RequestState.Idle) }
                                    eventChannel.send(
                                        Error(
                                            when (data) {
                                                DataError.Network.NotFound -> UiText.StringResource(
                                                    R.string.city_not_found
                                                )

                                                else -> data.asUiText()
                                            }
                                        )
                                    )
                                }
                        }
                    }
            }

            is SearchScreenAction.SetDefault -> {
                viewModelScope.launch(ioDispatcher) {
                    userPreferencesRepository.saveWeatherId(action.weather.id)
                }
            }

            SearchScreenAction.OnClearHistory -> {
                viewModelScope.launch(ioDispatcher) {
                    recordRepository.deleteAll()
                }
            }
        }
    }
}
