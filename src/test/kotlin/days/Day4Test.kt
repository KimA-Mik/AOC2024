package days

import com.github.kima_mik.days.Day4
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day4Test {
    private val input = getTestInput(4)
    private val day4 = Day4()

    @Test
    fun puzzle1() {
        assertEquals(18, day4.puzzle1(input))
        assertEquals(9, day4.puzzle2(input))
    }
}