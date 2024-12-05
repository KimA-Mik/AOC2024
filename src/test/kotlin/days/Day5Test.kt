package days

import com.github.kima_mik.days.Day5
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class Day5Test {
    private val input = Paths.get("inputs", "test_5.txt").toFile().readText()
    private val day5 = Day5()

    @Test
    fun puzzle1() {
        assertEquals(143, day5.puzzle1(input))
    }
}