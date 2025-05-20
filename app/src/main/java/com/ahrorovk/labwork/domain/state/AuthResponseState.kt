package com.ahrorovk.labwork.domain.state

import com.ahrorovk.labwork.data.model.auth.AuthResponse

data class AuthResponseState(
    val loading:Boolean = false,
    val response: AuthResponse? = null,
    val error:String = ""
)
