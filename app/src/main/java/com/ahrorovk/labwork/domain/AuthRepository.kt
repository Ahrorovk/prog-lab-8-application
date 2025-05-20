package com.ahrorovk.labwork.domain

import com.ahrorovk.labwork.data.model.auth.AuthRequest
import com.ahrorovk.labwork.data.model.auth.AuthResponse

interface AuthRepository {
    suspend fun signUp(
        authRequest: AuthRequest
    ): AuthResponse

    suspend fun login(
        authRequest: AuthRequest
    ): AuthResponse
}