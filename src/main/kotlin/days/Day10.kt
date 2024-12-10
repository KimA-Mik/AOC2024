package com.github.kima_mik.days

class Day10 : AOCDay(10) {
    class Field(val cells: IntArray, val width: Int, val height: Int) {
        override fun toString() = buildString {
            for (y in 0 until height) {
                for (x in 0 until width) {
                    append(cells[y * width + x])
                }
                append('\n')
            }
        }

        operator fun get(x: Int, y: Int) = cells[y * width + x]
        operator fun get(i: Int) = cells[i]
        fun getIndex(x: Int, y: Int) = y * width + x
        fun indexToCoordinates(index: Int): Pair<Int, Int> {
            val x = index % width
            val y = index / width

            return Pair(x, y)
        }
    }

    override fun puzzle1(input: String): Int {
        val field = extractField(input)

        return countTrails(field)
    }

    private fun extractField(input: String): Field {
        val lines = input.trim().lines()
        val w = lines[0].length
        val h = lines.size
        val intArray = IntArray(w * h)
        for (y in 0 until h) {
            for (x in 0 until w) {
                intArray[y * w + x] = lines[y][x].code - '0'.code
            }
        }

        return Field(intArray, w, h)
    }

    private fun countTrails(field: Field): Int {
        var result = 0

        for (i in field.cells.indices) {
            result += traceTrail(i, field)
        }

        return result
    }

    private fun traceTrail(startIndex: Int, field: Field): Int {
        if (field[startIndex] != 0) return 0
        val resultSet = mutableSetOf<Int>()
        val queue = ArrayDeque<Int>()
        queue.addLast(startIndex)

        while (queue.isNotEmpty()) {
            val curIndex = queue.removeFirst()
            val cur = field[curIndex]
            if (cur == 9) {
                resultSet.add(curIndex)
                continue
            }

            val (x, y) = field.indexToCoordinates(curIndex)

            val rightX = x + 1
            if (rightX in 0 until field.width && field[rightX, y] - cur == 1) {
                queue.addLast(field.getIndex(rightX, y))
            }

            val leftX = x - 1
            if (leftX in 0 until field.width && field[leftX, y] - cur == 1) {
                queue.addLast(field.getIndex(leftX, y))
            }

            val topY = y - 1
            if (topY in 0 until field.height && field[x, topY] - cur == 1) {
                queue.addLast(field.getIndex(x, topY))
            }

            val bottomY = y + 1
            if (bottomY in 0 until field.height && field[x, bottomY] - cur == 1) {
                queue.addLast(field.getIndex(x, bottomY))
            }
        }

        return resultSet.size
    }

    override fun puzzle2(input: String) = null
}