package com.ahrorovk.labwork.data.network

import com.ahrorovk.labwork.data.network.remote.AuthApi
import com.ahrorovk.labwork.domain.AuthRepository
import com.ahrorovk.labwork.data.model.auth.AuthRequest
import com.ahrorovk.labwork.data.model.auth.AuthResponse
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {
    override suspend fun signUp(authRequest: AuthRequest): AuthResponse =
        authApi.signUp(authRequest)

    override suspend fun login(authRequest: AuthRequest): AuthResponse =
        authApi.login(authRequest)
}