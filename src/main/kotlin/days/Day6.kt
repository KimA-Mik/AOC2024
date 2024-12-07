package com.github.kima_mik.days

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

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
        val fieldWidth: Int
        val fieldHeight: Int
        var totalVisitedCells = 0
            private set

        private val initialDirection: Direction
        private var direction: Direction
        private val field: CharArray
        private val initialX: Int
        private var posX: Int = 0
        private val initialY: Int
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

            initialX = posX
            initialY = posY
            direction = Direction.fromChar(getChar(posX, posY))
            initialDirection = direction
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
            totalVisitedCells += 1
            val nextX = posX + direction.xOffset
            val nextY = posY + direction.yOffset
            if (!onField(nextX, nextY)) {
                posX = nextX
                posY = nextY
                return
            }

            val nextC = getChar(nextX, nextY)
            if (nextC == OBSTACLE || nextC == LOOP_OBSTACLE) {
                direction = direction.rotate()
            } else {
                posX = nextX
                posY = nextY
            }
        }

        fun reset() {
            posX = initialX
            posY = initialY
            direction = initialDirection
            totalVisitedCells = 0

            for (i in field.indices) {
                val c = field[i]
                if (c != OBSTACLE && c != EMPTY) {
                    field[i] = EMPTY
                }
            }
        }

        fun visitedCellsCount() = field.count { it == VISITED }
        fun visitedCellsIndices() = buildList {
            for (i in field.indices) {
                if (field[i] == VISITED) {
                    add(i)
                }
            }
        }

        fun setLoopObstacle(x: Int, y: Int) {
            if (x == posX && y == posY) {
                return
            }
            setChar(x, y, LOOP_OBSTACLE)
        }

        fun getChar(x: Int, y: Int): Char {
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
            const val LOOP_OBSTACLE = 'O'
        }
    }

    fun puzzle1(input: String): Int {
        val sceneState = State(input)

        while (sceneState.isGuardOnField()) {
            sceneState.simulate()
        }

        return sceneState.visitedCellsCount()
    }

    fun puzzle2BruteForce(input: String): Int {
        val mutex = Mutex()
        var cores = Runtime.getRuntime().availableProcessors()
        if (cores > 1) cores -= 1
        var result = 0

        val scope = CoroutineScope(Dispatchers.Default)
        suspend fun incrementResult() = mutex.withLock {
            result += 1
        }

        val jobs = List(cores) { startOffset ->
            scope.launch {
                runConcurrentLoopCheck(
                    input = input,
                    startOffset = startOffset,
                    offset = cores,
                    increaseCounter = { incrementResult() }
                )
            }
        }

        runBlocking {
            jobs.joinAll()
        }

        return result
    }

    private suspend fun runConcurrentLoopCheck(
        input: String, startOffset: Int, offset: Int, increaseCounter: suspend () -> Unit
    ) {
        val state = State(input)
        while (state.isGuardOnField()) {
            state.simulate()
        }
        val visitedIndices = state.visitedCellsIndices()
        state.reset()

        for (i in startOffset..visitedIndices.lastIndex step offset) {
            val index = visitedIndices[i]
            val x = index % state.fieldWidth
            val y = index / state.fieldHeight

            state.setLoopObstacle(x, y)
            var isGuardOnField = state.isGuardOnField()
            while (isGuardOnField && state.totalVisitedCells / (state.visitedCellsCount() + 1) < 2) {
                state.simulate()
                isGuardOnField = state.isGuardOnField()
            }

            if (isGuardOnField) {
                increaseCounter()
            }
            state.reset()
        }
    }
}
