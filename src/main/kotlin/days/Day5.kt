package com.github.kima_mik.days

class Day5 {
    fun puzzle1(input: String): Int {
        var result = 0

        val pageOrderingRules = extractPageOrderingRules(input)
        val updates = extractUpdates(input)

        updates.forEach {
            result += validateUpdate(it, pageOrderingRules)
        }

        return result
    }

    private fun extractPageOrderingRules(input: String): Map<Int, Set<Int>> {
        //10|10
        return """\d+\|\d+"""
            .toRegex()
            .findAll(input)
            .map { it.value }
            .map { it.split('|') }
            .map { it.first().toInt() to it.last().toInt() }
            .groupBy(
                keySelector = { it.first },
                valueTransform = { it.second }
            )
            .mapValues { it.value.toSet() }
    }

    private fun extractUpdates(input: String): List<List<Int>> {
        //line of digits, followed by , or eol
        return """(\d+(,|$)){2,}"""
            .toRegex(RegexOption.MULTILINE)
            .findAll(input)
            .map { it.value }
            .map { it.split(',') }
            .map { it.map(String::toInt) }
            .toList()
    }

    private fun validateUpdate(update: List<Int>, rules: Map<Int, Set<Int>>): Int {
        for (i in update.indices) {
            val curRules = rules[update[i]] ?: continue
            for (j in 0 until i) {
                if (curRules.contains(update[j])) {
                    return 0
                }
            }
        }

        return update[update.size / 2]
    }
}