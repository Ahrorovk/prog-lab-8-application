package com.ahrorovk.labwork.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.labwork.core.Resource
import com.ahrorovk.labwork.data.local.dataStore.DataStoreManager
import com.ahrorovk.labwork.data.model.auth.AuthRequest
import com.ahrorovk.labwork.domain.state.AuthResponseState
import com.ahrorovk.labwork.domain.use_case.auth.LoginUseCase
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
class LoginViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        LoginState()
    )

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnLoginChange -> {
                _state.update {
                    it.copy(
                        login = event.login
                    )
                }
            }

            is LoginEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            LoginEvent.Login -> {
                login()
            }

            is LoginEvent.OnIsPreShowLoadingChange -> {
                _state.update {
                    it.copy(
                        isPreShowLoading = event.loading
                    )
                }
            }

            else -> {}
        }
    }

    private fun login() {
        loginUseCase.invoke(
            AuthRequest(
                _state.value.login,
                _state.value.password
            )
        ).onEach { result ->
            when (result) {
                is Resource.Error<*> -> {
                    _state.update {
                        it.copy(
                            authResponseState = AuthResponseState(error = result.message.toString())
                        )
                    }
                    Log.e("TAG", "ERROR-> ${result.message}")
                }

                is Resource.Loading<*> -> {
                    _state.update {
                        it.copy(
                            authResponseState = AuthResponseState(loading = true)
                        )
                    }
                }

                is Resource.Success<*> -> {
                    val response = result.data
                    if (response?.exitCode == 200) {
                        dataStoreManager.updateTokenState(response.responseObj)
                        _state.update {
                            it.copy(
                                authResponseState = AuthResponseState(response = response)
                            )
                        }
                        Log.i("TAG", "SUCCESS-> $response")
                    } else {
                        _state.update {
                            it.copy(
                                authResponseState = AuthResponseState(
                                    error = response?.message ?: "Something went wrong"
                                )
                            )
                        }
                        Log.e("TAG", "ERROR-> ${response?.message}")
                    }
                }
            }
        }.launchIn(viewModelScope + Dispatchers.IO)
    }
}