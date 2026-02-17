package com.herkiewaxmann.userdirectory.domain

sealed interface RemoteNetworkError : Error {
    data object GeneralNetworkError : RemoteNetworkError
    data object ParsingError : RemoteNetworkError
}
