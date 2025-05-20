package com.ahrorovk.labwork.data.model

import com.ahrorovk.labwork.data.model.lab_work.LabWork

data class UpdateLabWorkRequest(
    val id:Int,
    val labWork: LabWork
)
