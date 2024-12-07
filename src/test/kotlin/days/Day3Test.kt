package days

import com.github.kima_mik.days.Day3
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day3Test {
    private val input = getTestInput(3)
    private val input2 = getTestInput(3, 2)
    private val day3 = Day3()

    @Test
    fun extractMultiplyFromCorruptedMemory() {
        val res = day3.extractMultiplyFromCorruptedMemory(input)
        assertEquals(161, res)
    }

    @Test
    fun puzzle2() {
        val res = day3.damnItPuzzle2(input2)
        assertEquals(48L, res)
    }
}