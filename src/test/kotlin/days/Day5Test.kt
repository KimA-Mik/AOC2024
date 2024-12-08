package days

import com.github.kima_mik.days.Day5
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day5Test {
    private val input = getTestInput(5)
    private val day5 = Day5()

    @Test
    fun puzzle1() {
        assertEquals(143, day5.puzzle1(input))
        assertEquals(123, day5.puzzle2_1(input))
        assertEquals(123, day5.puzzle2_2(input))
    }
}