package com.github.kima_mik.days

import com.github.kima_mik.util.CharField
import com.github.kima_mik.util.Direction
import com.github.kima_mik.util.IntField

private const val START_CELL = 'S'
private const val END_CELL = 'E'
private const val WALL_CELL = '#'
private val INITIAL_DIRECTION = Direction.RIGHT
private const val ROTATION_PENALTY = 1000

class Day16 : AOCDay(16) {
    override fun puzzle1(input: String): Int {
        val field = CharField.extract(input)
        return calculateBestPath(field)
    }

    private data class Branch(
        val x: Int,
        val y: Int,
        val direction: Direction,
        val previousScore: Int,
        val rotationPenalty: Int = 0
    )

    private fun calculateBestPath(field: CharField): Int {
        val startIndex = field.cells.indexOfFirst { it == START_CELL }
        val endIndex = field.cells.indexOfFirst { it == END_CELL }
        val scores = IntField(IntArray(field.cells.size) { Int.MAX_VALUE }, field.width, field.height)

        val branches = ArrayDeque<Branch>()
        val (startX, startY) = field.indexToCoordinates(startIndex)
        branches.addLast(Branch(startX, startY, INITIAL_DIRECTION, -1))

        while (branches.isNotEmpty()) {
            val branch = branches.removeLast()
            val branchScore = branch.previousScore + branch.rotationPenalty

            val curIndex = field.getIndex(branch.x, branch.y)
            val newScore = branchScore + 1
            if (newScore >= scores[curIndex]) break
            scores[curIndex] = newScore

            for (direction in Direction.entries) {
                if (direction.isOpposite(branch.direction)) continue

                val rp = if (direction == branch.direction) 0 else ROTATION_PENALTY

                val dirX = branch.x + direction.xOffset
                val dirY = branch.y + direction.yOffset
                if (field[dirX, dirY] == WALL_CELL) continue

                val dirScore = scores[field.getIndex(dirX, dirY)]
                if (dirScore <= scores[curIndex] + rp + 1) continue

                branches.addLast(
                    Branch(dirX, dirY, direction, scores[curIndex], rp)
                )
            }
        }

        return scores[endIndex]
    }


    private fun Direction.isOpposite(other: Direction): Boolean {
        return this.xOffset + other.xOffset == 0 && this.yOffset + other.yOffset == 0
    }

    override fun puzzle2(input: String) = null
}