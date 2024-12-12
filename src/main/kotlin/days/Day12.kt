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

    override fun puzzle2(input: String): Int {
        val field = CharField.extract(input)
        val visited = mutableSetOf<Int>()
        var result = 0
        for (i in 0 until field.height * field.width) {
            if (visited.contains(i)) continue

            val indices = getRegionIndices(i, field, visited)
            val faces = countRegionFaces(indices, field)
//            println("A region of ${field[indices.first()]} plants with price ${indices.size} * $faces = ${indices.size * faces}")
            result += faces * indices.size
        }

        return result
    }

    private fun getRegionIndices(startIndex: Int, field: CharField, visited: MutableSet<Int>): List<Int> {
        val visitQueue = ArrayDeque<Int>()
        val indices = mutableListOf<Int>()

        visitQueue.addLast(startIndex)
        val currentPlant = field[startIndex]
        while (visitQueue.isNotEmpty()) {
            val currentIndex = visitQueue.removeLast()
            if (visited.contains(currentIndex)) continue

            visited.add(currentIndex)
            indices.add(currentIndex)
            val (x, y) = field.indexToCoordinates(currentIndex)
            for (direction in Direction.entries) {
                val x2 = x + direction.xOffset
                val y2 = y + direction.yOffset
                val index2 = field.getIndex(x2, y2)

                if (x2 in 0 until field.width &&
                    y2 in 0 until field.height &&
                    field[index2] == currentPlant &&
                    !visited.contains(index2)
                ) {
                    visitQueue.addLast(index2)
                }
            }
        }

        return indices
    }

    private fun countRegionFaces(indices: List<Int>, field: CharField): Int {
        var faces = 0
        val currentPlant = field[indices.first()]
        val coordinates = indices.sorted().map { field.indexToCoordinates(it) }
        val rows = coordinates.groupBy(keySelector = { it.second }, valueTransform = { it.first })
        val columns = coordinates.groupBy(keySelector = { it.first }, valueTransform = { it.second })

        var leftFace = false
        var rightFace = false
        for (column in columns) {
            val x = column.key
            var prevY = column.value.first()
            for (y in column.value) {
                if (y - prevY > 1) {
                    leftFace = false
                    rightFace = false
                }

                prevY = y
                val leftX = x - 1
                if (leftX < 0 || field[leftX, y] != currentPlant) {
                    if (!leftFace) {
                        leftFace = true
                        faces += 1
                    }
                } else {
                    leftFace = false
                }

                val rightX = x + 1
                if (rightX >= field.width || field[rightX, y] != currentPlant) {
                    if (!rightFace) {
                        rightFace = true
                        faces += 1
                    }
                } else {
                    rightFace = false
                }
            }

            leftFace = false
            rightFace = false
        }

        var topFace = false
        var bottomFace = false
        for (row in rows) {
            val y = row.key
            var prevX = row.value.first()
            for (x in row.value) {
                if (x - prevX > 1) {
                    topFace = false
                    bottomFace = false
                }

                prevX = x
                val topY = y - 1
                if (topY < 0 || field[x, topY] != currentPlant) {
                    if (!topFace) {
                        topFace = true
                        faces += 1
                    }
                } else {
                    topFace = false
                }

                val bottomY = y + 1
                if (bottomY >= field.height || field[x, bottomY] != currentPlant) {
                    if (!bottomFace) {
                        bottomFace = true
                        faces += 1
                    }
                } else {
                    bottomFace = false
                }
            }

            topFace = false
            bottomFace = false
        }

        return faces
    }
}