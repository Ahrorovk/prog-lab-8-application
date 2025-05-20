package com.ahrorovk.labwork.domain

import com.ahrorovk.labwork.data.model.DifficultyRequest
import com.ahrorovk.labwork.data.model.RemoveByIdRequest
import com.ahrorovk.labwork.data.model.Response
import com.ahrorovk.labwork.data.model.UpdateLabWorkRequest
import com.ahrorovk.labwork.data.model.discipline.DisciplineResponse
import com.ahrorovk.labwork.data.model.lab_work.LabWork
import com.ahrorovk.labwork.data.model.lab_work.LabWorkResponse
import com.ahrorovk.labwork.data.model.lab_work.LabWorksResponse

interface LabWorkRepository {
    suspend fun add(
        token: String,
        labWork: LabWork
    ): Response

    suspend fun addIfMax(
        token: String,
        labWork: LabWork
    ): Response

    suspend fun show(
        token: String
    ): LabWorksResponse

    suspend fun info(
        token: String
    ): Response

    suspend fun clear(
        token: String
    ): Response

    suspend fun update(
        token: String,
        updateLabWorkRequest: UpdateLabWorkRequest
    ): Response

    suspend fun removeById(
        token: String,
        removeByIdRequest: RemoveByIdRequest
    ): Response

    suspend fun removeGreater(
        token: String
    ): Response

    suspend fun removeLower(
        token: String
    ): Response

    suspend fun reorder(
        token: String
    ): Response

    suspend fun minByMaximumPoint(
        token: String
    ): LabWorkResponse

    suspend fun printUniqueDiscipline(
        token: String
    ): DisciplineResponse

    suspend fun filterGreaterDifficulty(
        token: String,
        difficultyRequest: DifficultyRequest
    ): LabWorksResponse
}