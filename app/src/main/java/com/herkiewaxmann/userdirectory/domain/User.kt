package com.herkiewaxmann.userdirectory.domain

import com.herkiewaxmann.userdirectory.data.DummyJSONUser

data class User(
    val id: Int,
    val name: String,
    val imageUrl: String? = null
)

val InvalidUser = User(-1, "Unknown")

class UserTransformer() {
    companion object {
        fun fromDummyJSONUser(user: DummyJSONUser) =
            User(
                id = user.id,
                name = "${user.firstName} ${user.lastName}",
                imageUrl = user.image
            )
    }
}
