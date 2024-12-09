package days

import com.github.kima_mik.days.Day9
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day9Test {
    private val input = getTestInput(9)
    private val day9 = Day9()

    @Test
    fun puzzle1() {
        assertEquals(1928L, day9.puzzle1(input))
    }
}