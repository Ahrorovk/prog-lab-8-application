package com.ahrorovk.labwork.domain.use_case.auth

import com.ahrorovk.labwork.core.Resource
import com.ahrorovk.labwork.domain.AuthRepository
import com.ahrorovk.labwork.data.model.auth.AuthRequest
import com.ahrorovk.labwork.data.model.auth.AuthResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(authRequest: AuthRequest): Flow<Resource<AuthResponse>> =
        flow {
            try {
                emit(Resource.Loading<AuthResponse>())
                val response = repository.login(authRequest)
                emit(Resource.Success<AuthResponse>(response))
            } catch (e: HttpException) {
                emit(
                    Resource.Error<AuthResponse>(
                        e.message() ?: "Error"
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error<AuthResponse>("Check your internet connection."))
            } catch (e: Exception) {
                emit(Resource.Error<AuthResponse>("${e.message}"))
            }
        }
}