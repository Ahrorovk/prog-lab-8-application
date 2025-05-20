package com.ahrorovk.labwork.data.network.remote

import com.ahrorovk.labwork.data.model.auth.AuthRequest
import com.ahrorovk.labwork.data.model.auth.AuthResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("sign-up")
    suspend fun signUp(
        @Body authRequest: AuthRequest
    ): AuthResponse

    @POST("login")
    suspend fun login(
        @Body authRequest: AuthRequest
    ): AuthResponse
}