package com.ahrorovk.labwork.domain.state

import com.ahrorovk.labwork.data.model.discipline.DisciplineResponse

data class DisciplineResponseState(
    val loading: Boolean = false,
    val response: DisciplineResponse? = null,
    val error: String = ""
)
