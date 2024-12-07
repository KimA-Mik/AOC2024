package days

import com.github.kima_mik.days.Day7
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day7Test {
    private val input = getTestInput(7)
    private val day7 = Day7()

    @Test
    fun puzzle1() {
        assertEquals(3749L, day7.puzzle1(input))
    }

    @Test
    fun puzzle2() {
        assertEquals(11387L, day7.puzzle2(input))
    }
}