package com.ahrorovk.labwork.data.network

import com.ahrorovk.labwork.data.model.DifficultyRequest
import com.ahrorovk.labwork.data.model.RemoveByIdRequest
import com.ahrorovk.labwork.data.model.Response
import com.ahrorovk.labwork.data.model.UpdateLabWorkRequest
import com.ahrorovk.labwork.data.model.discipline.DisciplineResponse
import com.ahrorovk.labwork.data.model.lab_work.LabWork
import com.ahrorovk.labwork.data.model.lab_work.LabWorkResponse
import com.ahrorovk.labwork.data.model.lab_work.LabWorksResponse
import com.ahrorovk.labwork.data.network.remote.LabWorkApi
import com.ahrorovk.labwork.domain.LabWorkRepository
import javax.inject.Inject

class LabWorkRepositoryImpl @Inject constructor(
    private val labWorkApi: LabWorkApi
) : LabWorkRepository {
    override suspend fun add(token: String, labWork: LabWork): Response =
        labWorkApi.add(token, labWork)

    override suspend fun addIfMax(token: String, labWork: LabWork): Response =
        labWorkApi.addIfMax(token, labWork)

    override suspend fun show(token: String): LabWorksResponse =
        labWorkApi.show(token)

    override suspend fun info(token: String): Response =
        labWorkApi.info(token)

    override suspend fun clear(token: String): Response =
        labWorkApi.clear(token)

    override suspend fun update(token: String, updateLabWorkRequest: UpdateLabWorkRequest): Response =
        labWorkApi.update(token, updateLabWorkRequest)

    override suspend fun removeById(token: String, removeByIdRequest: RemoveByIdRequest): Response =
        labWorkApi.removeById(token, removeByIdRequest)

    override suspend fun removeGreater(token: String): Response =
        labWorkApi.removeGreater(token)

    override suspend fun removeLower(token: String): Response =
        labWorkApi.removeLower(token)

    override suspend fun reorder(token: String): Response =
        labWorkApi.reorder(token)

    override suspend fun minByMaximumPoint(token: String): LabWorkResponse =
        labWorkApi.minByMaximumPoint(token)

    override suspend fun printUniqueDiscipline(token: String): DisciplineResponse =
        labWorkApi.printUniqueDiscipline(token)

    override suspend fun filterGreaterDifficulty(
        token: String,
        difficultyRequest: DifficultyRequest
    ): LabWorksResponse =
        labWorkApi.filterGreaterDifficulty(token, difficultyRequest)
}