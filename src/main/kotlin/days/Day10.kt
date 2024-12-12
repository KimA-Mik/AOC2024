package com.github.kima_mik.days

import com.github.kima_mik.util.IntField

class Day10 : AOCDay(10) {
    override fun puzzle1(input: String): Int {
        val field = extractField(input)

        return countTrails(field)
    }

    private fun extractField(input: String): IntField {
        val lines = input.trim().lines()
        val w = lines[0].length
        val h = lines.size
        val intArray = IntArray(w * h)
        for (y in 0 until h) {
            for (x in 0 until w) {
                intArray[y * w + x] = lines[y][x].code - '0'.code
            }
        }

        return IntField(intArray, w, h)
    }

    private fun countTrails(field: IntField): Int {
        var result = 0
        val headIndices = mutableSetOf<Int>()
        for (i in field.cells.indices) {
            traceTrail(i, field) { headIndices.add(it) }
            result += headIndices.size
            headIndices.clear()
        }

        return result
    }

    private fun traceTrail(startIndex: Int, field: IntField, recordHeadIndex: (headIndex: Int) -> Unit) {
        if (field[startIndex] != 0) return
        val queue = ArrayDeque<Int>()
        queue.addLast(startIndex)

        while (queue.isNotEmpty()) {
            val curIndex = queue.removeFirst()
            val cur = field[curIndex]
            if (cur == 9) {
                recordHeadIndex(curIndex)
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
    }

    override fun puzzle2(input: String): Int {
        val field = extractField(input)

        return rateTrails(field)
    }

    private fun rateTrails(field: IntField): Int {
        var result = 0
        for (i in field.cells.indices) {
            traceTrail(i, field) { result += 1 }
        }

        return result
    }
}