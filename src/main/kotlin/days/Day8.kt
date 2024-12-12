package com.github.kima_mik.days

import com.github.kima_mik.util.CharField
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

private const val ANTINODE_CELL = '#'
private const val EMPTY_CELL = '.'

class Day8 : AOCDay(8) {
    override fun puzzle1(input: String): Int {
        val field = CharField.extract(input)
        val antennas = extractAntennas(field)

        for (antenna in antennas) {
            plotAntinodes(antenna.value, field)
        }

        return field.cells.count { it == ANTINODE_CELL }
    }

    override fun puzzle2(input: String): Int {
        val field = CharField.extract(input)
        val antennas = extractAntennas(field)

        for (antenna in antennas) {
            plotAntinodes(antenna.value, field, checkDistance = false)
        }

        return field.cells.count { it == ANTINODE_CELL }
    }


    private fun extractAntennas(field: CharField): Map<Char, List<Int>> {
        val res = mutableMapOf<Char, MutableList<Int>>()
        for (i in field.cells.indices) {
            val c = field.cells[i]
            if (c == EMPTY_CELL) {
                continue
            }

            if (res.containsKey(c)) {
                res[c]!!.add(i)
            } else {
                res[c] = mutableListOf(i)
            }
        }

        return res
    }

    private fun plotAntinodes(positions: List<Int>, field: CharField, checkDistance: Boolean = true) {
        for (i in positions.indices) {
            val x1 = positions[i] % field.width
            val y1 = positions[i] / field.width
            for (j in i + 1 until positions.size) {
                val x2 = positions[j] % field.width
                val y2 = positions[j] / field.width

                plotAntinodesBetweenAntennas(x1, y1, x2, y2, field, checkDistance)
            }
        }
    }

    private fun plotAntinodesBetweenAntennas(
        x1: Int,
        y1: Int,
        x2: Int,
        y2: Int,
        field: CharField,
        checkDistance: Boolean
    ) {
        val dx = x2 - x1
        val dy = y2 - y1

        var x = x1
        var y = y1
        while (x in 0 until field.width && y in 0 until field.height) {
            if (checkDistance) {
                if (checkAntinodeDistance(x, y, x1, y1, x2, y2)) {
                    field.setAntinode(x, y)
                }
            } else {
                field.setAntinode(x, y)
            }

            x += dx
            y += dy
        }

        x = x1 - dx
        y = y1 - dy
        while (x in 0 until field.width && y in 0 until field.height) {
            if (checkDistance) {
                if (checkAntinodeDistance(x, y, x1, y1, x2, y2)) {
                    field.setAntinode(x, y)
                }
            } else {
                field.setAntinode(x, y)
            }

            x -= dx
            y -= dy
        }
    }

    private val eps = 1e-3
    private fun checkAntinodeDistance(pointX: Int, pointY: Int, x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
        val d1 = squareDistance(pointX, pointY, x1, y1).toFloat()
        val d2 = squareDistance(pointX, pointY, x2, y2).toFloat()

        if (d1 == 0f || d2 == 0f) return false
        return abs(max(d1, d2) / min(d1, d2) - 4f) < eps
    }

    private fun squareDistance(x1: Int, y1: Int, x2: Int, y2: Int): Int {
        val dx = x2 - x1
        val dy = y2 - y1

        return dx * dx + dy * dy
    }

    private fun CharField.setAntinode(x: Int, y: Int) {
        this[y * width + x] = ANTINODE_CELL
    }
}