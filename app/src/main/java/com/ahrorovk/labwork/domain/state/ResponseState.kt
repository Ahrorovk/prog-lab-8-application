package com.ahrorovk.labwork.domain.state

import com.ahrorovk.labwork.data.model.Response

data class ResponseState(
    val loading: Boolean = false,
    val response: Response? = null,
    val error: String = ""
)
