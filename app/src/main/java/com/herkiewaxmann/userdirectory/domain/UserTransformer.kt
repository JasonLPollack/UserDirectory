package com.herkiewaxmann.userdirectory.domain

import com.herkiewaxmann.userdirectory.data.DummyJSONUser

class UserTransformer() {
    companion object {
        fun fromDummyJSONUser(user: DummyJSONUser) =
            User(
                id = user.id,
                name = StringHelpers.createFullName(user.firstName, user.lastName),
                imageUrl = user.image
            )
    }
}