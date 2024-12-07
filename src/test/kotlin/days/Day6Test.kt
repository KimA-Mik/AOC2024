package days

import com.github.kima_mik.days.Day6
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day6Test {
    private val input = getTestInput(6)
    private val day6 = Day6()

    @Test
    fun puzzle1() {
        assertEquals(41, day6.puzzle1(input))
    }

    @Test
    fun puzzle2BruteForce() {
        assertEquals(6, day6.puzzle2BruteForce(input))
    }
}