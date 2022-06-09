package com.example.samplehiltretrofit.data.model

import com.example.samplehiltretrofit.util.ResultResource
import retrofit2.Response

abstract class BaseDataSource {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ResultResource<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ResultResource.SUCCESS(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): ResultResource<T> =
        ResultResource.ERROR("Api call failed $errorMessage")

}