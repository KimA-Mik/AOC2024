package days

import com.github.kima_mik.days.Day15
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day15Test {
    private val day15 = Day15()
    private val input = getTestInput(15)
    private val input2 = getTestInput(15, 2)

    @Test
    fun puzzle1() {
        assertEquals(2028, day15.puzzle1(input))
        assertEquals(10092, day15.puzzle1(input2))
    }
}