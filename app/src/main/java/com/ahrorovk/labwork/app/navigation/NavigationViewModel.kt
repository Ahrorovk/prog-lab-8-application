package com.ahrorovk.labwork.app.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.labwork.core.Resource
import com.ahrorovk.labwork.data.local.dataStore.DataStoreManager
import com.ahrorovk.labwork.domain.state.LabWorkResponseState
import com.ahrorovk.labwork.domain.use_case.lab_work.ShowLabWorksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val showLabWorksUseCase: ShowLabWorksUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(NavigationState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        NavigationState()
    )

    init {
        dataStoreManager.getTokenState.onEach { value ->
            Log.e("TAG", "datastore->$value")
            _state.update {
                it.copy(
                    tokenState = value
                )
            }
            Log.e("TAG", "datastore _state->${_state.value.tokenState}")
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: NavigationEvent) {
        when (event) {
            NavigationEvent.ShowLabWorks -> {
                viewModelScope.launch {
                    delay(500)
                    Log.e("TAG", "MyTOKEN->${_state.value.tokenState}")
                    if (_state.value.tokenState.isNotEmpty())
                        showLabWorks()
                }
            }

            NavigationEvent.ClearToken -> {
                viewModelScope.launch(Dispatchers.IO) {
                    dataStoreManager.updateTokenState("")
                }
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
                            response = LabWorkResponseState(error = result.message.toString())
                        )
                    }
                }

                is Resource.Loading<*> -> {
                    _state.update {
                        it.copy(
                            response = LabWorkResponseState(loading = true)
                        )
                    }
                }

                is Resource.Success<*> -> {
                    _state.update {
                        it.copy(
                            response = LabWorkResponseState(response = result.data)
                        )
                    }
                    Log.i("TAG", "SUCCESS-> ${result.data}\n${_state.value.response}")
                }
            }
        }.launchIn(viewModelScope)
    }
}