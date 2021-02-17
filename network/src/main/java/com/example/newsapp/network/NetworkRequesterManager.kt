package com.example.newsapp.network

import com.example.common.exceptions.BadRequestException
import com.example.common.exceptions.BadTokenException
import retrofit2.Response
import java.net.UnknownHostException

object NetworkRequesterManager {

    suspend fun <T> request(apiRequest: suspend () -> Response<T>): T {
        val genericMessageException by lazy {
            "Tente novamente mais tarde."
        }
        return try {
            val response = apiRequest()
            response.takeIf { it.isSuccessful }?.body()
                ?: throw when (response.code()) {
                    404 -> BadRequestException(response.code(), genericMessageException)
                    401 -> BadTokenException(response.code(), genericMessageException)
                    else -> Exception("Error in request")
                }
        } catch (error: Exception) {
            error.printStackTrace()
            throw if (error is UnknownHostException)
                BadRequestException(404, genericMessageException)
            else
                error
        }
    }

}