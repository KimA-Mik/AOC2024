package days

import com.github.kima_mik.days.Day12
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Test {
    private val input = getTestInput(12)
    private val day12 = Day12()

    @Test
    fun puzzle1() {
        assertEquals(1930, day12.puzzle1(input))
    }
}