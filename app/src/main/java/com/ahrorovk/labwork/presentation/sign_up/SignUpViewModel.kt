package com.ahrorovk.labwork.presentation.sign_up

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahrorovk.labwork.core.Resource
import com.ahrorovk.labwork.data.local.dataStore.DataStoreManager
import com.ahrorovk.labwork.data.model.auth.AuthRequest
import com.ahrorovk.labwork.domain.state.AuthResponseState
import com.ahrorovk.labwork.domain.use_case.auth.SignUpUseCase
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
class SignUpViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        SignUpState()
    )

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnSignUpChange -> {
                _state.update {
                    it.copy(
                        login = event.login
                    )
                }
            }

            is SignUpEvent.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            SignUpEvent.SignUp -> {
                signUp()
            }

            is SignUpEvent.OnIsPreShowLoadingChange -> {
                _state.update {
                    it.copy(
                        isPreShowLoading = event.loading
                    )
                }
            }

            else -> {}
        }
    }

    private fun signUp() {
        signUpUseCase.invoke(
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