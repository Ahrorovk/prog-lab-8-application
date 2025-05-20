package com.ahrorovk.labwork.data.model.lab_work

data class LabWorksResponse(
    val exitCode: Int,
    val message: String,
    val responseObj: List<LabWork>?
)
