package com.github.kima_mik.days

import com.github.kima_mik.util.IntField
import kotlin.math.max

private const val WIDTH = 101
private const val HEIGHT = 103
private const val DURATION = 100

private const val POSITION_PREFIX = "p="
private const val VELOCITY_PREFIX = "v="
private const val COORDINATES_DELIMITER = ","
private const val TYPES_DELIMITER = ' '

class Day14 : AOCDay(14) {
    data class Point(val x: Int, val y: Int) {
        companion object {
            fun fromString(string: String, delimiter: String = COORDINATES_DELIMITER): Point {
                val points = string.split(delimiter)
                return Point(points[0].toInt(), points[1].toInt())
            }
        }
    }

    data class Robot(val start: Point, val velocity: Point)

    override fun puzzle1(input: String): Int {
        val robots = extractInput(input)
        return calculateSafetyFactor(
            robots = robots,
            duration = DURATION,
            fieldWidth = WIDTH,
            fieldHeight = HEIGHT
        )
    }

    fun extractInput(input: String): List<Robot> {
        val robots = mutableListOf<Robot>()
        val lines = input.trim().lines()
        for (line in lines) {
            val split = line.split(TYPES_DELIMITER)
            val robot = Robot(
                start = Point.fromString(split[0].substringAfter(POSITION_PREFIX)),
                velocity = Point.fromString(split[1].substringAfter(VELOCITY_PREFIX))
            )

            robots.add(robot)
        }

        return robots
    }

    fun calculateSafetyFactor(
        robots: List<Robot>,
        duration: Int,
        fieldWidth: Int,
        fieldHeight: Int
    ): Int {
        val centerX = fieldWidth / 2
        val centerY = fieldHeight / 2

        var quadrant1 = 0
        var quadrant2 = 0
        var quadrant3 = 0
        var quadrant4 = 0

        for (robot in robots) {
            val x = (robot.start.x + robot.velocity.x * duration).fmod(fieldWidth)
            val y = (robot.start.y + robot.velocity.y * duration).fmod(fieldHeight)
            if (x < centerX) {
                if (y < centerY) quadrant1 += 1
                else if (y > centerY) quadrant4 += 1
            } else if (x > centerX) {
                if (y < centerY) quadrant2 += 1
                else if (y > centerY) quadrant3 += 1
            }
        }

        return quadrant1 * quadrant2 * quadrant3 * quadrant4
    }

    private fun Int.fmod(other: Int): Int {
        return (this % other + other) % other
    }

    override fun puzzle2(input: String): Int {
        val robots = extractInput(input)
        return simulateUntilTree(
            robots = robots,
            duration = 1000000,
            fieldWidth = WIDTH,
            fieldHeight = HEIGHT
        )
    }

    private fun simulateUntilTree(
        robots: List<Robot>,
        duration: Int,
        fieldWidth: Int,
        fieldHeight: Int
    ): Int {
        var intermediateRobots = robots
        repeat(duration) {
            val field = IntField(fieldWidth, fieldHeight)
            intermediateRobots = intermediateRobots.map { robot ->
                val x = (robot.start.x + robot.velocity.x).fmod(fieldWidth)
                val y = (robot.start.y + robot.velocity.y).fmod(fieldHeight)
                field[x, y] = 1
                Robot(Point(x, y), robot.velocity)
            }

            if (tryFindTree(field)) {
                println("${it + 1} seconds passed")
                println(field)
                return it + 1
            }
        }
        return -1
    }

    private fun tryFindTree(field: IntField): Boolean {
        var maxSize = Int.MIN_VALUE
        var continuation = 0

        for (y in 0 until field.height) {
            val longestLine = field.getLongestLine(y)
            if (longestLine > 5) {
                continuation++
            } else {
                maxSize = max(maxSize, continuation)
                continuation = 0
            }
        }

        return max(maxSize, continuation) > 5
    }

    private fun IntField.getLongestLine(y: Int): Int {
        var longest = Int.MIN_VALUE
        var continuation = 0

        for (x in 0 until width) {
            if (get(x, y) != 0) {
                continuation += 1
            } else {
                longest = max(longest, continuation)
                continuation = 0
            }
        }

        return max(longest, continuation)
    }
}