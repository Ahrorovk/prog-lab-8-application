package com.ahrorovk.labwork.domain.use_case.lab_work

import com.ahrorovk.labwork.core.Resource
import com.ahrorovk.labwork.data.model.lab_work.LabWorksResponse
import com.ahrorovk.labwork.domain.LabWorkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShowLabWorksUseCase @Inject constructor(
    private val repository: LabWorkRepository
) {
    operator fun invoke(token: String): Flow<Resource<LabWorksResponse>> = flow {
        try {
            emit(Resource.Loading<LabWorksResponse>())
            val response = repository.show(token)
            emit(Resource.Success<LabWorksResponse>(response))
        } catch (e: HttpException) {
            emit(Resource.Error<LabWorksResponse>(e.message() ?: "Error"))
        } catch (e: IOException) {
            emit(Resource.Error<LabWorksResponse>("Check your internet connection."))
        } catch (e: Exception) {
            emit(Resource.Error<LabWorksResponse>("${e.message}"))
        }
    }
}
