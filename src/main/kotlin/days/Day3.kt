package com.github.kima_mik.days

class Day3 : AOCDay(3) {
    override fun puzzle1(input: String): Long {
        var res = 0L
        Regex("""mul\(-?\d+,-?\d+\)""")
            .findAll(input)
            .map { it.value }
            .forEach { match ->
                res += evaluateMultiply(match)
            }

        return res
    }

    private val enableCommand = "do()"
    private val disableCommand = "don't()"
    override fun puzzle2(input: String): Long {
        var enabled = true
        var res = 0L
        """do\(\)|don't\(\)|mul\(\d+,\d+\)""".toRegex()
            .findAll(input)
            .map { it.value }
            .forEach {
                when (it) {
                    enableCommand -> {
                        enabled = true
                    }

                    disableCommand -> {
                        enabled = false
                    }

                    else -> {
                        res += evaluateMultiply(command = it, enabled = enabled)
                    }
                }
            }

        return res
    }

    private fun evaluateMultiply(command: CharSequence, enabled: Boolean = true): Long {
        if (!enabled) {
            return 0L
        }
        return Regex("""\d+""")
            .findAll(command)
            .map { it.value.toLong() }
            .reduce { acc, i -> acc * i }
    }
}