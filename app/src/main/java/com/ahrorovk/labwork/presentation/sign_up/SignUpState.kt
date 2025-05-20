package com.ahrorovk.labwork.presentation.sign_up

import com.ahrorovk.labwork.domain.state.AuthResponseState

data class SignUpState(
    val tokenState:String = "",
    val login:String = "",
    val password:String = "",
    val authResponseState: AuthResponseState = AuthResponseState(),
    val isPreShowLoading:Boolean = false
)
