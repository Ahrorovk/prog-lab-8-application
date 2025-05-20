package com.ahrorovk.labwork.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ahrorovk.labwork.presentation.components.ErrorScreen
import com.ahrorovk.labwork.presentation.components.LabWorkItem

@Composable
fun MainScreen(
    state: MainState,
    onEvent: (MainEvent) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        state.labWorkResponseState.response?.let { response ->
            response.responseObj?.let { resp ->
                LazyColumn {
                    items(resp) { labWork ->
                        LabWorkItem(labWork = labWork) {
                            onEvent(MainEvent.OnSelectedLabWorkIdChange(labWork.id.toInt()))
                        }
                    }
                }
            }
        }
        if (state.labWorkResponseState.response?.exitCode != 200)
            ErrorScreen(
                Modifier,
                state.labWorkResponseState.response?.message ?: state.labWorkResponseState.error
            )
    }
}