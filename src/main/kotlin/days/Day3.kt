package com.github.kima_mik.days

class Day3 {
    fun extractMultiplyFromCorruptedMemory(input: String): Long {
        var res = 0L
        val digitsRegex = """\d+""".toRegex()
        Regex("""mul\(-?\d+,-?\d+\)""")
            .findAll(input)
            .map { it.value }
            .forEach { match ->
                res += digitsRegex
                    .findAll(match)
                    .map { it.value.toLong() }
                    .reduce { acc, i -> acc * i }
            }

        return res
    }
}