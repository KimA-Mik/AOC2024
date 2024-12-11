package days

import com.github.kima_mik.days.Day11
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Test {
    private val input = getTestInput(11)
    private val day11 = Day11()

    @Test
    fun puzzle1() {
        assertEquals(55312, day11.puzzle1(input))
    }

    @Test
    fun puzzle2() {
        assertEquals(55312L, day11.puzzle2(input))
    }
}