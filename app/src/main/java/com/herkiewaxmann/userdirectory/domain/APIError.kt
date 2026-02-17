package com.herkiewaxmann.userdirectory.domain

sealed interface APIError : Error {
    data object GeneralNetworkError : APIError
    data object ParsingError : APIError
}
