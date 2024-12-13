package days

import com.github.kima_mik.days.Day13
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day13Test {
    private val input = getTestInput(13)
    private val day13 = Day13()

    @Test
    fun puzzle1() {
        assertEquals(480, day13.puzzle1(input))
    }
}