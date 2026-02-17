package com.herkiewaxmann.userdirectory.data

import com.herkiewaxmann.userdirectory.domain.DataStatus
import com.herkiewaxmann.userdirectory.domain.RemoteNetworkError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


@Serializable
data class DummyJSONAddress(
    val city: String? = null,
    val state: String? = null
)

@Serializable
data class DummyJSONUser(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val image: String,
    val age: Int,
    val email: String,
    val phone: String,
    val address: DummyJSONAddress
)


@Serializable
data class DummyJSONUserList(
    val users: List<DummyJSONUser>
)

interface UsersRepo {
    fun getUsers(): Flow<DataStatus<DummyJSONUserList, RemoteNetworkError>>

    fun getUsersByName(name: String): Flow<DataStatus<DummyJSONUserList, RemoteNetworkError>>

    fun getUserDetailById(id: Int): Flow<DataStatus<DummyJSONUser, RemoteNetworkError>>
}

class UsersRepoImpl: UsersRepo {
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

    /**
     * Fetch a list of all users (limited to 30 per API spec)
     */
    override fun getUsers(): Flow<DataStatus<DummyJSONUserList, RemoteNetworkError>> =
        flow {
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
                    emit(DataStatus.Error(RemoteNetworkError.GeneralNetworkError))
                }
            } catch (_: Exception) {
                emit(DataStatus.Error(RemoteNetworkError.ParsingError))
            }
        }.flowOn(Dispatchers.IO)

    /**
     * Fetch a list of users matching the given name
     */
    override fun getUsersByName(name: String): Flow<DataStatus<DummyJSONUserList, RemoteNetworkError>> =
        flow {
            emit(DataStatus.Loading)
            try {
                val response = client.get(BASE_URL) {
                    url {
                        appendPathSegments("users/search")
                        parameters.append("q", name)
                    }
                }
                if (response.status.value in 200..299) {
                    val userList = response.body<DummyJSONUserList>()
                    emit(DataStatus.Success(userList))
                }
                else if (response.status.value >= 400) {
                    emit(DataStatus.Error(RemoteNetworkError.GeneralNetworkError))
                }
            } catch (e: Exception) {
                emit(DataStatus.Error(RemoteNetworkError.ParsingError))
            }
        }.flowOn(Dispatchers.IO)

    /**
     * Get user details based on the given user id
     */
    override fun getUserDetailById(id: Int): Flow<DataStatus<DummyJSONUser, RemoteNetworkError>> =
        flow {
            emit(DataStatus.Loading)
            try {
                val response = client.get(BASE_URL) {
                    url {
                        appendPathSegments("users/$id")
                    }
                }
                if (response.status.value in 200..299) {
                    val user = response.body<DummyJSONUser>()
                    emit(DataStatus.Success(user))
                }
                else if (response.status.value >= 400) {
                    emit(DataStatus.Error(RemoteNetworkError.GeneralNetworkError))
                }
            } catch (_: Exception) {
                emit(DataStatus.Error(RemoteNetworkError.ParsingError))
            }
        }.flowOn(Dispatchers.IO)

}