package com.ahrorovk.labwork.data.network.remote

import com.ahrorovk.labwork.data.model.DifficultyRequest
import com.ahrorovk.labwork.data.model.RemoveByIdRequest
import com.ahrorovk.labwork.data.model.Response
import com.ahrorovk.labwork.data.model.UpdateLabWorkRequest
import com.ahrorovk.labwork.data.model.discipline.DisciplineResponse
import com.ahrorovk.labwork.data.model.lab_work.LabWork
import com.ahrorovk.labwork.data.model.lab_work.LabWorkResponse
import com.ahrorovk.labwork.data.model.lab_work.LabWorksResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LabWorkApi {
    @POST("add")
    suspend fun add(
        @Header("Authorization") token: String,
        @Body labWork: LabWork
    ): Response

    @POST("add-if-max")
    suspend fun addIfMax(
        @Header("Authorization") token: String,
        @Body labWork: LabWork
    ): Response

    @GET("show")
    suspend fun show(
        @Header("Authorization") token: String
    ): LabWorksResponse

    @GET("info")
    suspend fun info(
        @Header("Authorization") token: String
    ): Response

    @GET("clear")
    suspend fun clear(
        @Header("Authorization") token: String
    ): Response

    @POST("update")
    suspend fun update(
        @Header("Authorization") token: String,
        @Body updateLabWorkRequest: UpdateLabWorkRequest
    ): Response

    @POST("remove-by-id")
    suspend fun removeById(
        @Header("Authorization") token: String,
        @Body removeByIdRequest: RemoveByIdRequest
    ): Response

    @POST("remove-greater")
    suspend fun removeGreater(
        @Header("Authorization") token: String
    ): Response

    @POST("remove-lower")
    suspend fun removeLower(
        @Header("Authorization") token: String
    ): Response

    @POST("reorder")
    suspend fun reorder(
        @Header("Authorization") token: String
    ): Response

    @GET("min-by-maximum-point")
    suspend fun minByMaximumPoint(
        @Header("Authorization") token: String
    ): LabWorkResponse

    @GET("print-unique-discipline")
    suspend fun printUniqueDiscipline(
        @Header("Authorization") token: String
    ): DisciplineResponse

    @GET("filter-greater-difficulty")
    suspend fun filterGreaterDifficulty(
        @Header("Authorization") token: String,
        @Body difficultyRequest: DifficultyRequest
    ): LabWorksResponse


}