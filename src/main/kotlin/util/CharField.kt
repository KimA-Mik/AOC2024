package com.github.kima_mik.util

class CharField(val cells: CharArray, val width: Int, val height: Int) {
    constructor(width: Int, height: Int) : this(CharArray(width * height), width, height)

    override fun toString() = buildString {
        for (y in 0 until height) {
            for (x in 0 until width) {
                append(get(x, y))
            }
            append('\n')
        }
    }

    operator fun get(x: Int, y: Int) = cells[y * width + x]
    operator fun get(i: Int) = cells[i]
    operator fun set(i: Int, value: Char) {
        cells[i] = value
    }

    operator fun set(x: Int, y: Int, value: Char) = set(y * width + x, value)


    fun getIndex(x: Int, y: Int) = y * width + x
    fun indexToCoordinates(index: Int): Pair<Int, Int> {
        val x = index % width
        val y = index / width

        return Pair(x, y)
    }

    companion object {
        fun extract(input: String): CharField {
            val lines = input.trim().lines().map { it.trim() }

            return CharField(lines.joinToString("").toCharArray(), lines[0].length, lines.size)
        }
    }
}