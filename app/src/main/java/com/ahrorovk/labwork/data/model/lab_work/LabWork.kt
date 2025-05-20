package com.ahrorovk.labwork.data.model.lab_work;

data class LabWork(
    val id: Long,
    val name: String,
    val coordinates: Coordinates,
    val creationDate: String,
    val minimalPoint: Double,
    val maximumPoint: Int,
    val personalQualitiesMaximum: Int,
    val difficulty: String,
    val discipline: Discipline
)

