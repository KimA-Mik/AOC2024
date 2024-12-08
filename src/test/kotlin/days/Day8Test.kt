package days

import com.github.kima_mik.days.Day8
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day8Test {
    private val input = getTestInput(8)
    private val input2 = getTestInput(8, 2)
    private val day8 = Day8()

    @Test
    fun puzzle1() {
        assertEquals(14, day8.puzzle1(input))
    }

    @Test
    fun puzzle2() {
        assertEquals(34, day8.puzzle2(input))
        assertEquals(9, day8.puzzle2(input2))
    }
}