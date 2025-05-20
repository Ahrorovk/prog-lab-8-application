package com.ahrorovk.labwork.data.model.discipline

import com.ahrorovk.labwork.data.model.lab_work.Discipline

data class DisciplineResponse(
    val exitCode: Int,
    val message: String,
    val responseObj: List<Discipline>
)