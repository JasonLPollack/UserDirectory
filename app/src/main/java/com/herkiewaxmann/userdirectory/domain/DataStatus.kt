package com.herkiewaxmann.userdirectory.domain

typealias RootError = Error

sealed interface DataStatus<out T, out E: RootError> {
    object Loading: DataStatus<Nothing, Nothing>

    data class Error<out E: RootError>(val e: RootError): DataStatus<Nothing, E>

    data class Success<out T>(val data: T): DataStatus<T, Nothing>
}
