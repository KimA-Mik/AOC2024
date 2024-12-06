package com.github.kima_mik.days

class Day6 {
    private enum class Direction(val xOffset: Int, val yOffset: Int) {
        TOP(xOffset = 0, yOffset = -1),
        BOTTOM(xOffset = 0, yOffset = 1),
        LEFT(xOffset = -1, yOffset = 0),
        RIGHT(xOffset = 1, yOffset = 0);

        fun rotate(): Direction {
            return when (this) {
                TOP -> RIGHT
                BOTTOM -> LEFT
                LEFT -> TOP
                RIGHT -> BOTTOM
            }
        }

        override fun toString(): String {
            return when (this) {
                TOP -> "^"
                BOTTOM -> "v"
                RIGHT -> ">"
                LEFT -> "<"
            }
        }

        companion object {
            fun fromChar(c: Char): Direction {
                return when (c) {
                    '^' -> TOP
                    'v' -> BOTTOM
                    '>' -> RIGHT
                    '<' -> LEFT
                    else -> throw IllegalArgumentException("Invalid direction: $c")
                }
            }
        }
    }

    private class State(input: String) {
        private var direction: Direction
        private val fieldWidth: Int
        private val fieldHeight: Int
        private val field: CharArray
        private var posX: Int = 0
        private var posY: Int = 0

        init {
            val lines = input.trim().lines()
            fieldHeight = lines.size
            fieldWidth = lines.first().length
            field = lines.joinToString("").toCharArray()

            for (y in 0 until fieldHeight) {
                for (x in 0 until fieldHeight) {
                    val c = getChar(x, y)
                    if (c != EMPTY && c != OBSTACLE) {
                        posX = x
                        posY = y
                        break
                    }
                }
            }

            direction = Direction.fromChar(getChar(posX, posY))
        }

        fun isGuardOnField() = onField(posX, posY)
        fun fieldString() = buildString((fieldWidth + 1) * fieldHeight) {
            for (y in 0 until fieldHeight) {
                for (x in 0 until fieldWidth) {
                    val c = getChar(x, y)
                    if (x == posX && y == posY) {
                        append(direction.toString())
                    } else {
                        append(c)
                    }
                }
                append('\n')
            }
        }

        fun simulate() {
            setChar(posX, posY, VISITED)
            var nextX = posX + direction.xOffset
            var nextY = posY + direction.yOffset
            if (!onField(nextX, nextY)) {
                posX = nextX
                posY = nextY
                return
            }

            var nextC = getChar(nextX, nextY)
            while (nextC == OBSTACLE &&
                onField(nextX, nextY)
            ) {
                direction = direction.rotate()
                nextX = posX + direction.xOffset
                nextY = posY + direction.yOffset
                nextC = getChar(nextX, nextY)
            }

            posX = nextX
            posY = nextY
        }

        private fun getChar(x: Int, y: Int): Char {
            return field[y * fieldWidth + x]
        }

        private fun setChar(x: Int, y: Int, c: Char) {
            field[y * fieldWidth + x] = c
        }

        private fun onField(x: Int, y: Int) =
            x in 0 until fieldWidth && y in 0 until fieldHeight

        companion object {
            const val EMPTY = '.'
            const val OBSTACLE = '#'
            const val VISITED = 'X'
        }
    }

    fun puzzle1(input: String): Int {
        val sceneState = State(input)

        while (sceneState.isGuardOnField()) {
            sceneState.simulate()
        }

        val field = sceneState.fieldString()
        println("Scene:\n$field\n")
        return field.count { it == State.VISITED }
    }
}