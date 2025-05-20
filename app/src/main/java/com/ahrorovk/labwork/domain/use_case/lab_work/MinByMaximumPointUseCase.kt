package com.ahrorovk.labwork.domain.use_case.lab_work

import com.ahrorovk.labwork.core.Resource
import com.ahrorovk.labwork.data.model.lab_work.LabWorkResponse
import com.ahrorovk.labwork.domain.LabWorkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MinByMaximumPointUseCase @Inject constructor(
    private val repository: LabWorkRepository
) {
    operator fun invoke(token: String): Flow<Resource<LabWorkResponse>> = flow {
        try {
            emit(Resource.Loading<LabWorkResponse>())
            val response = repository.minByMaximumPoint(token)
            emit(Resource.Success<LabWorkResponse>(response))
        } catch (e: HttpException) {
            emit(Resource.Error<LabWorkResponse>(e.message() ?: "Error"))
        } catch (e: IOException) {
            emit(Resource.Error<LabWorkResponse>("Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error<LabWorkResponse>("${e.message}"))
        }
    }
}
