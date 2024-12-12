package com.github.kima_mik.util

enum class Direction(val xOffset: Int, val yOffset: Int) {
    TOP(xOffset = 0, yOffset = -1),
    BOTTOM(xOffset = 0, yOffset = 1),
    LEFT(xOffset = -1, yOffset = 0),
    RIGHT(xOffset = 1, yOffset = 0);

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
