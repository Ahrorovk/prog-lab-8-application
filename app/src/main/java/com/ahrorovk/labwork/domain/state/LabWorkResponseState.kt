package com.ahrorovk.labwork.domain.state

import com.ahrorovk.labwork.data.model.lab_work.LabWorksResponse

data class LabWorkResponseState(
    val loading: Boolean = false,
    val response: LabWorksResponse? = null,
    val error: String = ""
)
