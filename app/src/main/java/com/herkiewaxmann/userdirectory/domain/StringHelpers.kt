package com.herkiewaxmann.userdirectory.domain

object StringHelpers {
    /**
     * Create a full name based on first and last
     * If no information is present, return empty string
     */
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

    /**
     * Create a location based on city and state
     * If no information is present, return null
     */
    fun createLocation(city: String?, state: String?): String? = buildString {
        if (city != null) {
            append(city)
            if (state != null) {
                append(", $state")
            }
        } else {
            if (state != null) {
                append(state)
            } else {
                return@createLocation null
            }
        }
    }
}