package days

import com.github.kima_mik.days.Day4
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class Day4Test {
    private val input = Paths.get("inputs", "test_4.txt").toFile().readText()
    private val day4 = Day4()

    @Test
    fun puzzle1() {
        assertEquals(18, day4.puzzle1(input))
        assertEquals(9, day4.puzzle2(input))
    }
}