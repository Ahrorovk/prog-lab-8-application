package com.ahrorovk.labwork.presentation.add

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ahrorovk.labwork.data.model.lab_work.Difficulty
import com.ahrorovk.labwork.presentation.components.AddLabWorkField
import com.ahrorovk.labwork.presentation.components.CustomButton
import com.ahrorovk.labwork.presentation.components.DifficultyItem

@Composable
fun AddScreen(
    state: AddState,
    onEvent: (AddEvent) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                AddLabWorkField(Modifier, "Name", state.name) {
                    onEvent(AddEvent.OnNameChange(it))
                }
                Spacer(Modifier.padding(12.dp))
                AddLabWorkField(
                    Modifier,
                    "Coordinate X",
                    state.coordinatesX.toString(),
                    keyboardType = KeyboardType.Number
                ) {
                    onEvent(AddEvent.OnCoordinatesXChange(it.toLong()))
                }
                Spacer(Modifier.padding(12.dp))
                AddLabWorkField(
                    Modifier,
                    "Coordinate Y",
                    state.coordinatesY.toString(),
                    keyboardType = KeyboardType.Number
                ) {
                    onEvent(AddEvent.OnCoordinatesYChange(it.toLong()))
                }
                Spacer(Modifier.padding(12.dp))
                AddLabWorkField(
                    Modifier,
                    "Minimal point",
                    state.minimalPoint.toString(),
                    keyboardType = KeyboardType.Number
                ) {
                    onEvent(AddEvent.OnMinimalPointChange(it.toDouble()))
                }
                Spacer(Modifier.padding(12.dp))
                AddLabWorkField(
                    Modifier,
                    "Maximum point",
                    state.maximumPoint.toString(),
                    keyboardType = KeyboardType.Number
                ) {
                    onEvent(AddEvent.OnMaximumPointChange(it.toInt()))
                }
                Spacer(Modifier.padding(12.dp))
                AddLabWorkField(
                    Modifier,
                    "Maximum personal qualities",
                    state.personalQualitiesMaximum.toString(),
                    keyboardType = KeyboardType.Number
                ) {
                    onEvent(AddEvent.OnPersonalQualitiesMaximumChange(it.toInt()))
                }
                Spacer(Modifier.padding(12.dp))

                Text("Difficulties")
                LazyRow(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    items(Difficulty.entries.toTypedArray()) { diff ->
                        DifficultyItem(Modifier, diff.name, diff.name == state.difficulty) {
                            onEvent(AddEvent.OnDifficultyChange(it))
                        }
                        Spacer(Modifier.padding(12.dp))
                    }
                }

                Spacer(Modifier.padding(12.dp))
                AddLabWorkField(
                    Modifier,
                    "Discipline name",
                    state.disciplineName
                ) {
                    onEvent(AddEvent.OnDisciplineNameChange(it))
                }
                Spacer(Modifier.padding(12.dp))
                AddLabWorkField(
                    Modifier,
                    "Discipline Practice hours",
                    state.disciplinePracticeHours.toString(),
                    keyboardType = KeyboardType.Number
                ) {
                    onEvent(AddEvent.OnDisciplinePracticeHoursChange(it.toInt()))
                }
                Spacer(Modifier.padding(12.dp))
                CustomButton(text = "Add", textSize = 16, isLoading = state.responseState.loading) {
                    when (state.selectedId) {
                        -1 -> {
                            onEvent(AddEvent.AddLabWork)
                        }

                        -2 -> {
                            onEvent(AddEvent.AddIfMaxLabWork)
                        }

                        else -> {
                            onEvent(AddEvent.UpdateLabWork)
                        }
                    }
                    onEvent(AddEvent.BackToMain)
                }
            }
        }
    }
}