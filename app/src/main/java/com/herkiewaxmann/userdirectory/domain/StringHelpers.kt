package com.herkiewaxmann.userdirectory.domain

object StringHelpers {
    fun createFullName(first: String?, last: String?) = buildString {
        if (first != null) {
            append(first)
            if (last != null) {
                append(' ')
                append(last)
            }
        } else {
            if (last != null) {
                append(last)
            }
        }
    }

    fun createLocation(city: String?, state: String?) = buildString {
        if (city != null) {
            append(city)
            if (state != null) {
                append(", $state")
            }
        } else {
            if (state != null) {
                append(state)
            }
        }
    }
}