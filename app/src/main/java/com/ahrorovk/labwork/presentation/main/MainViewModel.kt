package com.ahrorovk.labwork.presentation.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.labwork.core.Resource
import com.ahrorovk.labwork.data.local.dataStore.DataStoreManager
import com.ahrorovk.labwork.data.model.RemoveByIdRequest
import com.ahrorovk.labwork.domain.state.LabWorkResponseState
import com.ahrorovk.labwork.domain.state.ResponseState
import com.ahrorovk.labwork.domain.use_case.lab_work.RemoveByIdUseCase
import com.ahrorovk.labwork.domain.use_case.lab_work.ShowLabWorksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val showLabWorksUseCase: ShowLabWorksUseCase,
    private val removeByIdUseCase: RemoveByIdUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        MainState()
    )

    init {
        dataStoreManager.getTokenState.onEach { value ->
            _state.update {
                it.copy(
                    tokenState = value
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnLabWorkResponseStateChange -> {
                _state.update {
                    it.copy(
                        labWorkResponseState = event.responseState
                    )
                }
            }

            is MainEvent.OnSelectedIdChange -> {
                _state.update {
                    it.copy(
                        selectedLabWorkId = event.id
                    )
                }
            }

            MainEvent.RemoveById -> {
                removeById()
            }

            else -> Unit
        }
    }

    private fun showLabWorks() {
        showLabWorksUseCase.invoke(_state.value.tokenState).onEach { result ->
            when (result) {
                is Resource.Error<*> -> {
                    _state.update {
                        it.copy(
                            labWorkResponseState = LabWorkResponseState(error = result.message.toString())
                        )
                    }
                }

                is Resource.Loading<*> -> {
                    _state.update {
                        it.copy(
                            labWorkResponseState = LabWorkResponseState(loading = true)
                        )
                    }
                }

                is Resource.Success<*> -> {
                    _state.update {
                        it.copy(
                            labWorkResponseState = LabWorkResponseState(response = result.data)
                        )
                    }
                    Log.i("TAG", "SUCCESS-> ${result.data}\n${_state.value.labWorkResponseState}")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun removeById() {
        removeByIdUseCase.invoke(
            _state.value.tokenState,
            RemoveByIdRequest(_state.value.selectedLabWorkId)
        ).onEach { result ->
            when (result) {
                is Resource.Error<*> -> {
                    _state.update {
                        it.copy(
                            responseState = ResponseState(error = result.message.toString())
                        )
                    }
                }

                is Resource.Loading<*> -> {
                    _state.update {
                        it.copy(
                            responseState = ResponseState(loading = true)
                        )
                    }
                }

                is Resource.Success<*> -> {
                    _state.update {
                        it.copy(
                            responseState = ResponseState(response = result.data)
                        )
                    }
                    showLabWorks()
                    Log.i("TAG", "SUCCESS-> ${result.data}\n${_state.value.responseState}")
                }
            }
        }.launchIn(viewModelScope)
    }
}