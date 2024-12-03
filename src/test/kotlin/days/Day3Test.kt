package days

import com.github.kima_mik.days.Day3
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class Day3Test {
    private val input = Paths.get("inputs", "test_3.txt").toFile().readText()
    private val input2 = Paths.get("inputs", "test_3_2.txt").toFile().readText()
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