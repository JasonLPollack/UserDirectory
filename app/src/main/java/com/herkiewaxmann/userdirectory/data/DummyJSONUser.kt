package com.herkiewaxmann.userdirectory.data

import kotlinx.serialization.Serializable

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
