package com.ahrorovk.labwork.data.model.lab_work

data class LabWorkResponse(
    val exitCode: Int,
    val message: String,
    val responseObj: LabWork?
)
