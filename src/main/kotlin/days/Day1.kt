package com.github.kima_mik.days

import kotlin.math.abs

class Day1 {
    fun totalDistanceBetweenLists(input: String): Int {
        val (leftList, rightList) = extractSortedLists(input)

        var totalDistance = 0
        for (i in leftList.indices) {
            totalDistance += abs(leftList[i] - rightList[i])
        }

        return totalDistance
    }


    fun similarityScore(input: String): Int {
        class Counter(var right: Int) {
            var left = 1
        }

        val resMap = mutableMapOf<Int, Counter>()
        val (leftList, rightList) = extractSortedLists(input)
        var current: Int
        var rightI = 0

        for (left in leftList) {
            if (resMap.containsKey(left)) {
                resMap[left]!!.left += 1
                continue
            }

            current = left
            var accumulator = 0
            while (rightI < rightList.size && rightList[rightI] <= current) {
                if (rightList[rightI] == current) {
                    accumulator++
                }
                rightI++
            }

            resMap[current] = Counter(accumulator)
        }

        return resMap.map { it.key * it.value.left * it.value.right }.sum()
    }

    private fun extractSortedLists(input: String): Pair<List<Int>, List<Int>> {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        val lines = input.lines().filter { it.isNotBlank() }
        for (i in lines.indices) {
            val (left, right) = extractLine(lines[i])
            leftList.add(left)
            rightList.add(right)
        }

        leftList.sort()
        rightList.sort()
        return leftList to rightList
    }

    private fun extractLine(line: String): Pair<Int, Int> {
        val nums = line.split(' ')
        val first = nums.first().toInt()
        val second = nums.last().toInt()

        return first to second
    }
}
