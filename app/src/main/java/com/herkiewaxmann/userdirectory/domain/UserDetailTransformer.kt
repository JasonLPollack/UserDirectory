package com.herkiewaxmann.userdirectory.domain

import com.herkiewaxmann.userdirectory.data.DummyJSONUser


class UserDetailTransformer() {
    companion object {
        fun fromDummyJSONUser(user: DummyJSONUser) =
            UserDetail(
                id = user.id,
                name = StringHelpers.createFullName(user.firstName, user.lastName),
                imageUrl = user.image,
                age = user.age,
                email = user.email,
                phone = user.phone,
                location = StringHelpers.createLocation(user.address.city, user.address.state)
            )
    }
}