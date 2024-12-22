package com.fooddelivery.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `separate with dots and check if it is true`() {

        val list = listOf("a", "b", "c")
        val result = separateListWithDots(list)

        assertEquals("a • b • c", result)
    }

    @Test
    fun `separate with dots and one additional dot at the end and check if it is false`() {

        val list = listOf("a", "b", "c")
        val result = separateListWithDots(list)

        assertNotEquals("a • b • c • ", result)
    }

    @Test
    fun `single list element with no dot check if it is without dot`() {

        val list = listOf("a")
        val result = separateListWithDots(list)

        assertEquals("a", result)
    }

    @Test
    fun `show 1 as 1 min`(){

        val input = 1

        val result = minuteToHour(input)

        assertEquals("1 min", result)

    }

    @Test
    fun `show 2 as 2 mins`(){

        val input = 2

        val result = minuteToHour(input)

        assertEquals("2 mins", result)

    }

    @Test
    fun `show 60 as 1 hour`(){

        val input = 60

        val result = minuteToHour(input)

        assertEquals("1 hour", result)

    }

    @Test
    fun `show 61 as 1 hour 1 min`(){

        val input = 61

        val result = minuteToHour(input)

        assertEquals("1 hour 1 min", result)

    }

    @Test
    fun `show 62 as 1 hour 2 mins`(){

        val input = 62

        val result = minuteToHour(input)

        assertEquals("1 hour 2 mins", result)

    }

    @Test
    fun `show 145 as 2 hours 25 mins`(){

        val input = 145

        val result = minuteToHour(input)

        assertEquals("2 hours 25 mins", result)

    }
}