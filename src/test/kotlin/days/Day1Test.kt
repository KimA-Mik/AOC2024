package days

import com.github.kima_mik.days.Day1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.file.Paths

class Day1Test {
    private val input = Paths.get( "inputs", "test_1.txt").toFile().readText()
    private val day1 = Day1()

    @Test
    fun totalDistanceBetweenLists() {
        val res = day1.totalDistanceBetweenLists(input)
        assertEquals(11, res)
    }

    @Test
    fun similarityScore(){
        val res = day1.similarityScore(input)
        assertEquals(31, res)
    }
}