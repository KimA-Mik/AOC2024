package com.github.kima_mik.days

import com.github.kima_mik.util.CharField
import com.github.kima_mik.util.Direction

class Day12 : AOCDay(12) {
    override fun puzzle1(input: String): Int {
        val field = CharField.extract(input)
        val visited = mutableSetOf<Int>()
        val visitQueue = ArrayDeque<Int>()
        val regionPrices = mutableListOf<Int>()

        for (i in 0 until field.height * field.width) {
            if (visited.contains(i)) {
                continue
            }

            visitQueue.addLast(i)
            val currentPlant = field[i]

            var area = 0
            var perimeter = 0
            while (visitQueue.isNotEmpty()) {
                val currentIndex = visitQueue.removeLast()
                if (visited.contains(currentIndex)) continue

                visited.add(currentIndex)
                area += 1
                val (x, y) = field.indexToCoordinates(currentIndex)
                for (direction in Direction.entries) {
                    val x2 = x + direction.xOffset
                    val y2 = y + direction.yOffset
                    val index2 = field.getIndex(x2, y2)

                    if (x2 !in 0 until field.width || y2 !in 0 until field.height) {
                        perimeter += 1
                        continue
                    }

                    if (field[index2] == currentPlant &&
                        !visited.contains(index2)
                    ) {
                        visitQueue.addLast(index2)
                    } else if (field[index2] != currentPlant) {
                        perimeter += 1
                    }
                }
            }

            val price = area * perimeter
            if (price > 0) {
                regionPrices.add(price)
            }
        }

        return regionPrices.sum()
    }

    override fun puzzle2(input: String) = null
}