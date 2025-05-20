package com.ahrorovk.labwork.presentation.add

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.labwork.core.Resource
import com.ahrorovk.labwork.core.toMMDDYYYY
import com.ahrorovk.labwork.data.local.dataStore.DataStoreManager
import com.ahrorovk.labwork.data.model.UpdateLabWorkRequest
import com.ahrorovk.labwork.data.model.lab_work.Coordinates
import com.ahrorovk.labwork.data.model.lab_work.Discipline
import com.ahrorovk.labwork.data.model.lab_work.LabWork
import com.ahrorovk.labwork.domain.state.ResponseState
import com.ahrorovk.labwork.domain.use_case.lab_work.AddIfMaxLabWorkUseCase
import com.ahrorovk.labwork.domain.use_case.lab_work.AddLabWorkUseCase
import com.ahrorovk.labwork.domain.use_case.lab_work.UpdateLabWorkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val addLabWorkUseCase: AddLabWorkUseCase,
    private val addIfMaxLabWorkUseCase: AddIfMaxLabWorkUseCase,
    private val updateLabWorkUseCase: UpdateLabWorkUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(AddState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        AddState()
    )

    init {
        dataStoreManager.getTokenState.onEach { value ->
            _state.update {
                it.copy(
                    tokenState = value
                )
            }
        }.launchIn(viewModelScope + Dispatchers.IO)
    }

    fun onEvent(event: AddEvent) {
        when (event) {
            AddEvent.AddIfMaxLabWork -> {
                addIfMaxLabWork()
            }

            AddEvent.AddLabWork -> {
                addLabWork()
            }

            AddEvent.UpdateLabWork -> {
                updateLabWork()
            }

            is AddEvent.OnIdChange -> {
                _state.update {
                    it.copy(
                        selectedId = event.id
                    )
                }
            }

            is AddEvent.OnCoordinatesXChange -> {
                _state.update {
                    it.copy(
                        coordinatesX = event.x
                    )
                }
            }

            is AddEvent.OnCoordinatesYChange -> {
                _state.update {
                    it.copy(
                        coordinatesY = event.y
                    )
                }
            }

            is AddEvent.OnDifficultyChange -> {
                _state.update {
                    it.copy(
                        difficulty = event.difficulty
                    )
                }
            }

            is AddEvent.OnDisciplineNameChange -> {
                _state.update {
                    it.copy(
                        disciplineName = event.name
                    )
                }
            }

            is AddEvent.OnDisciplinePracticeHoursChange -> {
                _state.update {
                    it.copy(
                        disciplinePracticeHours = event.practiceHours
                    )
                }
            }

            is AddEvent.OnMaximumPointChange -> {
                _state.update {
                    it.copy(
                        maximumPoint = event.maximumPoint
                    )
                }
            }

            is AddEvent.OnMinimalPointChange -> {
                _state.update {
                    it.copy(
                        minimalPoint = event.minimalPoint
                    )
                }
            }

            is AddEvent.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = event.name
                    )
                }
            }

            is AddEvent.OnPersonalQualitiesMaximumChange -> {
                _state.update {
                    it.copy(
                        personalQualitiesMaximum = event.personalQualitiesMaximum
                    )
                }
            }

            is AddEvent.OnLabWorkChange -> {
                _state.update {
                    it.copy(
                        name = event.labWork.name,
                        coordinatesX = event.labWork.coordinates.x,
                        coordinatesY = event.labWork.coordinates.y,
                        minimalPoint = event.labWork.minimalPoint,
                        creationDate = event.labWork.creationDate,
                        maximumPoint = event.labWork.maximumPoint,
                        personalQualitiesMaximum = event.labWork.personalQualitiesMaximum,
                        difficulty = event.labWork.difficulty,
                        disciplineName = event.labWork.discipline.name,
                        disciplinePracticeHours = event.labWork.discipline.practiceHours
                    )
                }
            }

            else -> {}
        }
    }

    private fun addLabWork() {
        addLabWorkUseCase.invoke(
            _state.value.tokenState,
            LabWork(
                1000L,
                _state.value.name,
                Coordinates(_state.value.coordinatesX, _state.value.coordinatesY),
                System.currentTimeMillis().toMMDDYYYY(),
                _state.value.minimalPoint,
                _state.value.maximumPoint,
                _state.value.personalQualitiesMaximum,
                _state.value.difficulty,
                Discipline(_state.value.disciplineName, _state.value.disciplinePracticeHours)
            )
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
                    Log.i("TAG", "SUCCESS-> ${result.data}\n${_state.value.responseState}")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun addIfMaxLabWork() {
        addIfMaxLabWorkUseCase.invoke(
            _state.value.tokenState,
            LabWork(
                1000L,
                _state.value.name,
                Coordinates(_state.value.coordinatesX, _state.value.coordinatesY),
                System.currentTimeMillis().toMMDDYYYY(),
                _state.value.minimalPoint,
                _state.value.maximumPoint,
                _state.value.personalQualitiesMaximum,
                _state.value.difficulty,
                Discipline(_state.value.disciplineName, _state.value.disciplinePracticeHours)
            )
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
                    Log.i("TAG", "SUCCESS-> ${result.data}\n${_state.value.responseState}")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateLabWork() {
        updateLabWorkUseCase.invoke(
            _state.value.tokenState,
            UpdateLabWorkRequest(
                _state.value.selectedId,
                LabWork(
                    1000L,
                    _state.value.name,
                    Coordinates(_state.value.coordinatesX, _state.value.coordinatesY),
                    _state.value.creationDate,
                    _state.value.minimalPoint,
                    _state.value.maximumPoint,
                    _state.value.personalQualitiesMaximum,
                    _state.value.difficulty,
                    Discipline(_state.value.disciplineName, _state.value.disciplinePracticeHours)
                )
            )
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
                    Log.i("TAG", "SUCCESS-> ${result.data}\n${_state.value.responseState}")
                }
            }
        }.launchIn(viewModelScope)
    }

}