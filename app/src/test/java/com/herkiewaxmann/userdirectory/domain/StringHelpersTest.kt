package com.herkiewaxmann.userdirectory.domain

import junit.framework.TestCase.assertEquals
import org.junit.Test

class StringHelpersTest {
    @Test
    fun createFullName() {
        val result = StringHelpers.createFullName("John", "Doe")
        assertEquals("John Doe", result)
    }

    @Test
    fun `createFullName without Last Name`() {
        val result = StringHelpers.createFullName("John", null)
        assertEquals("John", result)
    }

    @Test
    fun `createFullName without First Name`() {
        val result = StringHelpers.createFullName(null, "Doe")
        assertEquals("Doe", result)
    }

    @Test
    fun `createFullName without Any Name returns empty string`() {
        val result = StringHelpers.createFullName(null, null)
        assertEquals("", result)
    }

    @Test
    fun createLocation() {
        val result = StringHelpers.createLocation("New York", "NY")
        assertEquals("New York, NY", result)
    }

    @Test
    fun `createLocation without State`() {
        val result = StringHelpers.createLocation("New York", null)
        assertEquals("New York", result)
    }

    @Test
    fun `createLocation without City`() {
        val result = StringHelpers.createLocation(null, "Utah")
        assertEquals("Utah", result)
    }

    @Test
    fun `createLocation without any location information returns null`() {
        val result = StringHelpers.createLocation(null, null)
        assertEquals(null, result)
    }
}