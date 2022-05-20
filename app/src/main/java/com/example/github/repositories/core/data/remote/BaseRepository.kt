package com.example.github.repositories.core.data.remote

import com.example.github.repositories.core.domain.ApiResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.Exception

abstract class BaseRepository(
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun <T> executeRequest(request: suspend () -> Response<T>): ApiResult<T> {
        return try {
            withContext(ioDispatcher) {
                val response = request.invoke()
                if (response.isSuccessful) {
                    val body = response.body()
                    body?.let { return@withContext ApiResult.Success(it) }
                }
                ApiResult.Error(exception = Exception("${response.code()} : ${response.message()}"))
            }
        } catch (e: HttpException) {
            ApiResult.Error(exception = e)
        } catch (e: IOException) {
            //handles no internet exception
            ApiResult.Error(exception = e)
        }
    }
}