package com.herkiewaxmann.userdirectory.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

sealed interface DataStatus<out T> {
    object Loading: DataStatus<Nothing>
    data class Error(val exception: Exception): DataStatus<Nothing>
    data class Success<out T>(val data: T): DataStatus<T>
}

@Serializable
data class DummyJSONUser(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val image: String
)

@Serializable
data class DummyJSONUserList(
    val users: List<DummyJSONUser>
)

class UsersRepo {
    companion object {
        const val BASE_URL = "https://dummyjson.com"
    }
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    fun getUsers(): Flow<DataStatus<DummyJSONUserList>> = flow {
        emit(DataStatus.Loading)
        try {
            val response = client.get(BASE_URL) {
                url {
                    appendPathSegments("users")
                }
            }
            if (response.status.value in 200..299) {
                val userList = response.body<DummyJSONUserList>()
                emit(DataStatus.Success(userList))
            }
            else if (response.status.value >= 400) {
                emit(DataStatus.Error(Exception(response.status.description)))
            }
        } catch (e: Exception) {
            println("Bad bad bad")
            emit(DataStatus.Error(Exception(e.localizedMessage)))
        }
    }

}