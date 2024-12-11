package com.github.kima_mik.days

class Day11 : AOCDay(11) {
    override fun puzzle1(input: String): Int {
        var line = extractInput(input)

        repeat(25) {
            line = simulateBlink(line)
        }

        return line.size
    }

    private fun extractInput(input: String): LongArray {
        val strings = input.trim().split(' ')
        val result = LongArray(strings.size)
        for (i in strings.indices) {
            result[i] = strings[i].toLong()
        }

        return result
    }

    private fun simulateBlink(line: LongArray): LongArray {
        var newLength = 0
        for (stone in line) {
            newLength += when {
                stone == 0L -> 1
                numberLength(stone) % 2 == 0 -> 2
                else -> 1
            }
        }
        val newLine = LongArray(newLength)
        var newIndex = 0

        for (i in line.indices) {
            val stone = line[i]
            when {
                stone == 0L -> {
                    newLine[newIndex++] = 1
                }

                numberLength(stone) % 2 == 0 -> {
                    val (left, right) = stone.splitInHalf()
                    newLine[newIndex++] = left
                    newLine[newIndex++] = right
                }

                else -> {
                    newLine[newIndex++] = stone * 2024L
                }
            }
        }


        return newLine
    }

    private fun numberLength(number: Long): Int {
        var temp = number
        var result = 0

        while (temp > 0L) {
            result += 1
            temp /= 10
        }

        return result
    }

    private fun Long.splitInHalf(): Pair<Long, Long> {
        val length = numberLength(this)


        var half = 1
        repeat(length / 2) {
            half *= 10
        }

        val left = this / half
        val right = this % half

        return left to right
    }

    override fun puzzle2(input: String): Long {
        var line = extractInput(input)
            .groupBy { it }
            .mapValues { it.value.size.toLong() }
            .toMutableMap()

        repeat(75) {
            line = simulateBlink(line)
        }

        return line.values.sum()
    }

    private fun simulateBlink(line: MutableMap<Long, Long>): MutableMap<Long, Long> {
        val result = mutableMapOf<Long, Long>()
        for (group in line) {
            when {
                group.key == 0L -> {
                    result.increaseKey(1, group.value)
                }

                numberLength(group.key) % 2 == 0 -> {
                    val (left, right) = group.key.splitInHalf()
                    result.increaseKey(left, group.value)
                    result.increaseKey(right, group.value)
                }

                else -> {
                    result.increaseKey(group.key * 2024L, group.value)
                }
            }
        }

        return result
    }

    private fun MutableMap<Long, Long>.increaseKey(key: Long, times: Long) {
        if (containsKey(key)) {
            this[key] = this[key]!! + times
        } else {
            this[key] = times
        }
    }
}