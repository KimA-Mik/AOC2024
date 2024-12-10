package days

import com.github.kima_mik.days.Day10
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Test {
    private val input = getTestInput(10)
    private val input2 = getTestInput(10, 2)
    private val day10 = Day10()

    @Test
    fun puzzle1() {
        assertEquals(1, day10.puzzle1(input))
        assertEquals(36, day10.puzzle1(input2))
    }

    @Test
    fun puzzle2() {
        assertEquals(81, day10.puzzle2(input2))
    }
}