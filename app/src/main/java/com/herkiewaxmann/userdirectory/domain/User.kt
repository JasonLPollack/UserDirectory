package com.herkiewaxmann.userdirectory.domain

data class User(
    val id: Int,
    val name: String,
    val imageUrl: String? = null
)
