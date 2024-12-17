package days

import com.github.kima_mik.days.Day16
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16Test {
    private val day16 = Day16()
    private val input = getTestInput(16)
    private val input2 = getTestInput(16, 2)

    @Test
    fun puzzle1() {
        assertEquals(7036, day16.puzzle1(input))
        assertEquals(11048, day16.puzzle1(input2))
    }
}