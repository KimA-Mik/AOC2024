package days

import com.github.kima_mik.days.Day14
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day14Test {
    private val input = getTestInput(14)
    private val day14 = Day14()

    @Test
    fun extractInput() {
        val expected = listOf(
            Day14.Robot(Day14.Point(0, 4), Day14.Point(3, -3)),
            Day14.Robot(Day14.Point(6, 3), Day14.Point(-1, -3)),
            Day14.Robot(Day14.Point(10, 3), Day14.Point(-1, 2)),
            Day14.Robot(Day14.Point(2, 0), Day14.Point(2, -1)),
            Day14.Robot(Day14.Point(0, 0), Day14.Point(1, 3)),
            Day14.Robot(Day14.Point(3, 0), Day14.Point(-2, -2)),
            Day14.Robot(Day14.Point(7, 6), Day14.Point(-1, -3)),
            Day14.Robot(Day14.Point(3, 0), Day14.Point(-1, -2)),
            Day14.Robot(Day14.Point(9, 3), Day14.Point(2, 3)),
            Day14.Robot(Day14.Point(7, 3), Day14.Point(-1, 2)),
            Day14.Robot(Day14.Point(2, 4), Day14.Point(2, -3)),
            Day14.Robot(Day14.Point(9, 5), Day14.Point(-3, -3))
        )

        val actual = day14.extractInput(input)
        assertEquals(expected, actual)
    }


    @Test
    fun puzzle1() {
        val actual = day14.calculateSafetyFactor(
            robots = day14.extractInput(input),
            duration = 100,
            fieldWidth = 11,
            fieldHeight = 7
        )
        assertEquals(12, actual)
    }
}