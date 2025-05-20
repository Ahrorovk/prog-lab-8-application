package com.ahrorovk.labwork.domain.use_case.lab_work

import com.ahrorovk.labwork.core.Resource
import com.ahrorovk.labwork.data.model.discipline.DisciplineResponse
import com.ahrorovk.labwork.domain.LabWorkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PrintUniqueDisciplineUseCase @Inject constructor(
    private val repository: LabWorkRepository
) {
    operator fun invoke(token: String): Flow<Resource<DisciplineResponse>> = flow {
        try {
            emit(Resource.Loading<DisciplineResponse>())
            val response = repository.printUniqueDiscipline(token)
            emit(Resource.Success<DisciplineResponse>(response))
        } catch (e: HttpException) {
            emit(Resource.Error<DisciplineResponse>(e.message() ?: "Error"))
        } catch (e: IOException) {
            emit(Resource.Error<DisciplineResponse>("Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error<DisciplineResponse>("${e.message}"))
        }
    }
}
