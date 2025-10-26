package com.me.weather.presentation.features.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.me.weather.domain.model.AppDispatchers
import com.me.weather.domain.model.Dispatcher
import com.me.weather.domain.model.ResponseResource
import com.me.weather.domain.model.Weather
import com.me.weather.domain.model.toRecord
import com.me.weather.domain.repository.RecordRepository
import com.me.weather.domain.repository.UserPreferencesRepository
import com.me.weather.domain.use_case.LoadDataUseCase
import com.me.weather.presentation.utils.RequestState
import com.me.weather.presentation.utils.RequestState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    init {
        viewModelScope.launch(ioDispatcher) {
            recordRepository
                .getAll()
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
                            when (val res = loadDataUseCase(query = action.query)) {
                                is ResponseResource.Error -> {
                                    _uiState.update { it.copy(weatherRequestState = RequestState.Idle) }
                                }

                                is ResponseResource.Success<Weather> -> {
                                    recordRepository.add(res.data.toRecord())

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

            is SearchScreenAction.SetDefault -> {
                viewModelScope.launch(ioDispatcher) {
                    userPreferencesRepository.saveWeatherId(action.weather.id)
                }
            }
        }
    }
}
