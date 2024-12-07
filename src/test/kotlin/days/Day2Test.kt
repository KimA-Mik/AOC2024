package days

import com.github.kima_mik.days.Day2
import getTestInput
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day2Test {
    private val input = getTestInput(2)
    private val day2 = Day2()

    @Test
    fun countSafeReports() {
        val res = day2.countSafeReports(input)
        assertEquals(2, res)
    }

    @Test
    fun countSafeReportsWithDampener() {
        val resDumb = day2.countSafeReportsWithDampenerNSquaredVersion(input)
        assertEquals(4, resDumb)

        val res = day2.countSafeReportsWithDampener(input)
        assertEquals(4, res)
    }
}