package com.github.kima_mik.days

import com.github.kima_mik.util.CharField
import com.github.kima_mik.util.Direction

private const val OBSTACLE_CELL = '#'
private const val FREE_CELL = '.'
private const val BOX_CELL = 'O'
private const val ROBOT_CELL = '@'

class Day15 : AOCDay(15) {
    private data class Warehouse(val field: CharField, val moves: List<Direction>) {
        fun calculateSumOfGpsCoordinates(): Int {
            var result = 0
            for (y in 0 until field.height) {
                for (x in 0 until field.width) {
                    if (field[x, y] == BOX_CELL) {
                        result += 100 * y + x
                    }
                }
            }

            return result
        }
    }

    override fun puzzle1(input: String): Int {
        val warehouse = extractInput(input)
//        println("Initial state:\n${warehouse.field}")
        for (move in warehouse.moves) {
            simulateMove(move, warehouse.field)
//            println("Move $move:\n${warehouse.field}")
        }

        return warehouse.calculateSumOfGpsCoordinates()
    }

    private fun extractInput(input: String): Warehouse {
        val lines = input.trim().lines()

        val split = lines.indexOfFirst { it.isBlank() }
        val fieldLines = lines.subList(0, split)
        val field = CharField(fieldLines.joinToString("").toCharArray(), fieldLines[0].length, fieldLines.size)

        val moves = mutableListOf<Direction>()
        val movesLines = lines.subList(split + 1, lines.size)
        for (movesLine in movesLines) {
            for (c in movesLine) {
                moves.add(Direction.fromChar(c))
            }
        }

        return Warehouse(field, moves)
    }

    private fun simulateMove(move: Direction, field: CharField) {
        val (robotX, robotY) = field.robotPosition()
        val newX = robotX + move.xOffset
        val newY = robotY + move.yOffset

        when (field[newX, newY]) {
            OBSTACLE_CELL -> return
            FREE_CELL -> {
                field[newX, newY] = ROBOT_CELL
                field[robotX, robotY] = FREE_CELL
            }

            BOX_CELL -> tryMoveBoxes(
                field = field,
                move = move,
                robotX = robotX,
                robotY = robotY,
            )

            else -> throw Exception("Unexpected field '$field'")
        }
    }

    private fun tryMoveBoxes(field: CharField, move: Direction, robotX: Int, robotY: Int) {
        var xCursor = robotX + move.xOffset * 2
        var yCursor = robotY + move.yOffset * 2
        while (field[xCursor, yCursor] != FREE_CELL) {
            if (field[xCursor, yCursor] == OBSTACLE_CELL) {
                return
            }

            xCursor += move.xOffset
            yCursor += move.yOffset
        }

        while (field[xCursor, yCursor] != ROBOT_CELL) {
            field[xCursor, yCursor] = field[xCursor - move.xOffset, yCursor - move.yOffset]
            xCursor -= move.xOffset
            yCursor -= move.yOffset
        }

        field[robotX, robotY] = FREE_CELL
    }

    private fun CharField.robotPosition() = indexToCoordinates(cells.indexOfFirst { it == ROBOT_CELL })

    override fun puzzle2(input: String) = null
}