package days

import com.github.kima_mik.days.Day1
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day1Test {
    private val input = getTestInput(1)
    private val day1 = Day1()

    @Test
    fun totalDistanceBetweenLists() {
        val res = day1.puzzle1(input)
        assertEquals(11, res)
    }

    @Test
    fun similarityScore(){
        val res = day1.puzzle2(input)
        assertEquals(31, res)
    }
}