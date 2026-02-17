package com.herkiewaxmann.userdirectory.domain

data class UserDetail(
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
    val age: Int? = null,
    val email: String? = null,
    val phone: String? = null,
    val location: String? = null,
)

