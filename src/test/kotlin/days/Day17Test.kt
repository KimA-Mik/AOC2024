package days

import com.github.kima_mik.days.Day17
import getTestInput
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day17Test {
    private val day17 = Day17()
    private val input = getTestInput(17)
    private val input2 = getTestInput(17, 2)

    @Test
    fun cocutorBstTest() {
        val cocutor = Day17.Cocutor(regC = 9, program = listOf(2, 6))
        cocutor.run()
        assertEquals(0, cocutor.regA)
        assertEquals(1, cocutor.regB)
        assertEquals(9, cocutor.regC)
    }

    @Test
    fun cocutorOutputTest() {
        val cocutor = Day17.Cocutor(regA = 10, program = listOf(5, 0, 5, 1, 5, 4))
        cocutor.run()
        assertEquals("0,1,2", cocutor.stdOut)
    }

    @Test
    fun cocutorOutputAndJunpTest() {
        val cocutor = Day17.Cocutor(regA = 2024, program = listOf(0, 1, 5, 4, 3, 0))
        cocutor.run()
        assertEquals("4,2,5,6,7,7,7,7,3,1,0", cocutor.stdOut)
    }

    @Test
    fun cocutorBxlTest() {
        val cocutor = Day17.Cocutor(regB = 29, program = listOf(1, 7))
        cocutor.run()
        assertEquals(0, cocutor.regA)
        assertEquals(26, cocutor.regB)
        assertEquals(0, cocutor.regC)
    }

    @Test
    fun cocutorBcxTest() {
        val cocutor = Day17.Cocutor(regB = 2024, regC = 43690, program = listOf(4, 0))
        cocutor.run()
        assertEquals(0, cocutor.regA)
        assertEquals(44354, cocutor.regB)
        assertEquals(43690, cocutor.regC)
    }

    @Test
    fun puzzle1() {
        assertEquals("4,6,3,5,6,3,5,2,1,0", day17.puzzle1(input))
    }

    @Test
    fun `test possibility of p2`() {
        val cocutor = Day17.Cocutor(regA = 117440, program = listOf(0, 3, 5, 4, 3, 0))
        cocutor.run()
        assertEquals("0,3,5,4,3,0", cocutor.stdOut)
    }

    @Test
    fun puzzle2() {
        assertEquals(117440, day17.puzzle2(input2))
    }
}