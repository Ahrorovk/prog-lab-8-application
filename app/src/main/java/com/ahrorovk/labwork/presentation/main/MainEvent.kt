package com.ahrorovk.labwork.presentation.main

import com.ahrorovk.labwork.domain.state.LabWorkResponseState

sealed class MainEvent {
    data class OnLabWorkResponseStateChange(val responseState: LabWorkResponseState) : MainEvent()
    data class OnSelectedLabWorkIdChange(val id: Int) : MainEvent()
    data class OnSelectedIdChange(val id: Int) : MainEvent()
    object RemoveById : MainEvent()
}