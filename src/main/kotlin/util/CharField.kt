package com.github.kima_mik.util

class CharField(val cells: CharArray, val width: Int, val height: Int) {
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
    fun getIndex(x: Int, y: Int) = y * width + x
    fun indexToCoordinates(index: Int): Pair<Int, Int> {
        val x = index % width
        val y = index / width

        return Pair(x, y)
    }
}