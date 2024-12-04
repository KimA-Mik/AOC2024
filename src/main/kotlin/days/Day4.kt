package com.github.kima_mik.days

class Day4 {
    fun puzzle1(input: String): Int {
        val lines = input.lines()
        var total = 0

        for (y in lines.indices) {
            for (x in lines[y].indices) {
                val spaceAbove = y > 2
                val spaceRight = lines[y].lastIndex - x > 2
                val spaceLeft = x > 2
                val spaceBelow = lines.lastIndex - y > 2
                if (lines[y][x] != 'X')
                    continue

                if (spaceAbove) {
                    total += findXmas { lines[y - it][x] }
                }
                if (spaceAbove && spaceRight) {
                    total += findXmas { lines[y - it][x + it] }
                }
                if (spaceRight) {
                    total += findXmas { lines[y][x + it] }
                }
                if (spaceBelow && spaceRight) {
                    total += findXmas { lines[y + it][x + it] }
                }
                if (spaceBelow) {
                    total += findXmas { lines[y + it][x] }
                }
                if (spaceBelow && spaceLeft) {
                    total += findXmas { lines[y + it][x - it] }
                }
                if (spaceLeft) {
                    total += findXmas { lines[y][x - it] }
                }
                if (spaceAbove && spaceLeft) {
                    total += findXmas { lines[y - it][x - it] }
                }
            }
        }

        return total
    }

    private val letters = listOf('M', 'A', 'S')
    private fun findXmas(op: (letterOffset: Int) -> Char): Int {
        var found = true
        for (i in letters.indices) {
            if (op(i + 1) != letters[i]) {
                found = false
                break
            }
        }

        return if (found) 1 else 0
    }
}