package com.ahrorovk.labwork.presentation.add

import com.ahrorovk.labwork.domain.state.LabWorkResponseState
import com.ahrorovk.labwork.domain.state.ResponseState

data class AddState(
    val tokenState: String = "",
    val selectedId: Int = 0,
    val name: String = "",
    val coordinatesX: Long = 0,
    val coordinatesY: Long = 0,
    val creationDate: String = "",
    val minimalPoint: Double = 0.0,
    val maximumPoint: Int = 0,
    val personalQualitiesMaximum: Int = 0,
    val difficulty: String = "",
    val disciplineName: String = "",
    val disciplinePracticeHours: Int = 0,
    val responseState: ResponseState = ResponseState(),
    val response: LabWorkResponseState = LabWorkResponseState()

)
