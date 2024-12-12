package days

import com.github.kima_mik.days.Day12
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {
    private val input = getTestInput(12)
    private val input2 = getTestInput(12, 2)
    private val input3 = getTestInput(12, 3)
    private val day12 = Day12()

    @Test
    fun puzzle1() {
        assertEquals(1930, day12.puzzle1(input))
    }

    @Test
    fun puzzle2() {
        assertEquals(1206, day12.puzzle2(input))
        assertEquals(236, day12.puzzle2(input2)) {
            "Shrek, but only EEE"
        }
        assertEquals(368, day12.puzzle2(input3))
    }
}