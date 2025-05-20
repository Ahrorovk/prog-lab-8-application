package com.ahrorovk.labwork.presentation.login

import com.ahrorovk.labwork.domain.state.AuthResponseState

data class LoginState(
    val tokenState:String = "",
    val login:String = "",
    val password:String = "",
    val authResponseState: AuthResponseState = AuthResponseState(),
    val isPreShowLoading:Boolean = false
)
