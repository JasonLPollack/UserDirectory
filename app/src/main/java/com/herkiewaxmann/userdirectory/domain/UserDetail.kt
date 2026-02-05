package com.herkiewaxmann.userdirectory.domain

import com.herkiewaxmann.userdirectory.data.DummyJSONUser

data class UserDetail(
    val id: Int,
    val name: String,
    val imageUrl: String? = null,
    val age: Int? = null,
    val email: String? = null,
    val phone: String? = null,
    val city: String? = null,
    val state: String? = null
)

class UserDetailTransformer() {
    companion object {
        fun fromDummyJSONUser(user: DummyJSONUser) =
            UserDetail(
                id = user.id,
                name = "${user.firstName} ${user.lastName}",
                imageUrl = user.image,
                age = user.age,
                email = user.email,
                phone = user.phone,
                city = user.address.city,
                state = user.address.state
            )
    }
}
