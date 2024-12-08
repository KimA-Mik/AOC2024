package com.github.kima_mik.days

class Day4 : AOCDay(4) {
    override fun puzzle1(input: String): Int {
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

    override fun puzzle2(input: String): Int {
        val lines = input.lines()
        var total = 0

        for (y in 1 until lines.lastIndex) {
            for (x in 1 until lines[y].lastIndex) {
                if (lines[y][x] != 'A') {
                    continue
                }
                total += findCrossXmas(lines, x, y)
            }
        }

        return total
    }

    private val set = setOf('M', 'S')
    private fun findCrossXmas(input: List<String>, x: Int, y: Int): Int {
        var top = input[y - 1][x - 1]
        var bottom = input[y + 1][x + 1]
        if (top == bottom || !set.contains(top) || !set.contains(bottom)) {
            return 0
        }

        top = input[y - 1][x + 1]
        bottom = input[y + 1][x - 1]
        if (top == bottom || !set.contains(top) || !set.contains(bottom)) {
            return 0
        }

        return 1
    }
}