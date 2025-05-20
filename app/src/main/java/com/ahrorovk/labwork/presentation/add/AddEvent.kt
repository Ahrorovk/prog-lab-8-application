package com.ahrorovk.labwork.presentation.add

import com.ahrorovk.labwork.data.model.lab_work.LabWork

sealed class AddEvent {
    data class OnIdChange(val id: Int) : AddEvent()
    data class OnNameChange(val name: String) : AddEvent()
    data class OnDisciplineNameChange(val name: String) : AddEvent()
    data class OnDisciplinePracticeHoursChange(val practiceHours: Int) : AddEvent()
    data class OnDifficultyChange(val difficulty: String) : AddEvent()
    data class OnCoordinatesXChange(val x: Long) : AddEvent()
    data class OnCoordinatesYChange(val y: Long) : AddEvent()
    data class OnLabWorkChange(val labWork: LabWork) : AddEvent()
    data class OnMinimalPointChange(val minimalPoint: Double) : AddEvent()
    data class OnMaximumPointChange(val maximumPoint: Int) : AddEvent()
    data class OnPersonalQualitiesMaximumChange(val personalQualitiesMaximum: Int) : AddEvent()
    object AddLabWork : AddEvent()
    object AddIfMaxLabWork : AddEvent()
    object UpdateLabWork : AddEvent()
    object BackToMain : AddEvent()
}