package com.ahrorovk.labwork.app.navigation

import com.ahrorovk.labwork.domain.state.LabWorkResponseState

data class NavigationState(
    val languageState: String = "",
    val tokenState: String = "",
    val selectedId: Int = 0,
    val response: LabWorkResponseState = LabWorkResponseState()
)
