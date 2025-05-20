package com.ahrorovk.labwork.presentation.main

import com.ahrorovk.labwork.domain.state.LabWorkResponseState
import com.ahrorovk.labwork.domain.state.ResponseState

data class MainState(
    val tokenState: String = "",
    val responseState: ResponseState = ResponseState(),
    val labWorkResponseState: LabWorkResponseState = LabWorkResponseState(),
    val selectedLabWorkId:Int = 0
)
