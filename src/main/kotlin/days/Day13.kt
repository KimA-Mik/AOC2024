package com.github.kima_mik.days

private const val PREFIX_A = "Button A: "
private const val PREFIX_B = "Button B: "
private const val PREFIX_PRIZE = "Prize: "

private const val X_PLUS = "X+"
private const val Y_PLUS = "Y+"
private const val X_EQUALS = "X="
private const val Y_EQUALS = "Y="

class Day13 : AOCDay(13) {
    data class Point(val x: Long, val y: Long)
    data class Machine(val buttonA: Point, val buttonB: Point, val target: Point)

    override fun puzzle1(input: String): Int {
        var result = 0
        val machines = extractInput(input)
        for (machine in machines) {
            result += countTokens(machine)
        }

        return result
    }

    private fun extractInput(input: String): List<Machine> {
        val result = mutableListOf<Machine>()
        val lines = input.trim().lines()
        var buttonA: Point? = null
        var buttonB: Point? = null
        var target: Point? = null

        for (line in lines) {
            if (line.isBlank()) {
                buttonA = null
                buttonB = null
                target = null
                continue
            }

            when {
                line.startsWith(PREFIX_A) -> {
                    val split = line.substringAfter(PREFIX_A).split(", ")
                    buttonA = Point(split[0].substringAfter(X_PLUS).toLong(), split[1].substringAfter(Y_PLUS).toLong())
                }

                line.startsWith(PREFIX_B) -> {
                    val split = line.substringAfter(PREFIX_B).split(", ")
                    buttonB = Point(split[0].substringAfter(X_PLUS).toLong(), split[1].substringAfter(Y_PLUS).toLong())
                }

                line.startsWith(PREFIX_PRIZE) -> {
                    val split = line.substringAfter(PREFIX_PRIZE).split(", ")
                    target =
                        Point(split[0].substringAfter(X_EQUALS).toLong(), split[1].substringAfter(Y_EQUALS).toLong())
                }

                else -> throw Error("Unexpected line $line")
            }

            if (buttonA != null &&
                buttonB != null &&
                target != null
            ) {
                result.add(Machine(buttonA, buttonB, target))
            }
        }

        return result
    }

    private fun countTokens(machines: Machine): Int {
        var smallestCount = Int.MAX_VALUE
        for (a in 0 until 100) {
            for (b in 0 until 100) {
                val x = machines.buttonA.x * a + machines.buttonB.x * b
                val y = machines.buttonA.y * a + machines.buttonB.y * b
                if (x != machines.target.x || y != machines.target.y) {
                    continue
                }

                smallestCount = minOf(smallestCount, 3 * a + b)
            }
        }

        return if (smallestCount < Int.MAX_VALUE) smallestCount else 0
    }

    override fun puzzle2(input: String) = null
}