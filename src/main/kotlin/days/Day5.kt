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

    fun puzzle2(input: String): Int {
        var result = 0

        val pageOrderingRules = extractPageOrderingRules(input)
        val updates = extractUpdates(input)

        for (update in updates) {
            if (validateUpdate(update, pageOrderingRules) > 0) continue

            result += reorderUpdate(update, pageOrderingRules)
        }

        return result
    }

    private fun reorderUpdate(update: List<Int>, rules: Map<Int, Set<Int>>): Int {
        val sorted = update.sortedWith { o1, o2 ->
            val rule = rules[o1] ?: return@sortedWith 0
            if (rule.contains(o2)) {
                -1
            } else {
                0
            }
        }

        return sorted[sorted.size / 2]
    }
}