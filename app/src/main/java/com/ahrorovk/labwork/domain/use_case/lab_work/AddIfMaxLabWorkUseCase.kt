package com.ahrorovk.labwork.domain.use_case.lab_work

import com.ahrorovk.labwork.core.Resource
import com.ahrorovk.labwork.data.model.Response
import com.ahrorovk.labwork.data.model.lab_work.LabWork
import com.ahrorovk.labwork.domain.LabWorkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddIfMaxLabWorkUseCase @Inject constructor(
    private val repository: LabWorkRepository
) {
    operator fun invoke(token: String, labWork: LabWork): Flow<Resource<Response>> = flow {
        try {
            emit(Resource.Loading<Response>())
            val response = repository.addIfMax(token, labWork)
            emit(Resource.Success<Response>(response))
        } catch (e: HttpException) {
            emit(Resource.Error<Response>(e.message() ?: "Error"))
        } catch (e: IOException) {
            emit(Resource.Error<Response>("Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error<Response>("${e.message}"))
        }
    }
}
