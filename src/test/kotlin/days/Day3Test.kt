package days

import com.github.kima_mik.days.Day3
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class Day3Test {
    private val input = Paths.get("inputs", "test_3.txt").toFile().readText()
    private val day3 = Day3()

    @Test
    fun extractMultiplyFromCorruptedMemory() {
        val res = day3.extractMultiplyFromCorruptedMemory(input)
        kotlin.test.assertEquals(161, res)
    }
}