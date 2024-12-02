package com.github.kima_mik.days

import kotlin.math.abs
import kotlin.math.sign

private const val MAX_SAFE_DIFFERENCE = 3

class Day2 {
    fun countSafeReports(input: String): Int {
        val reports = extractReports(input)

        var res = 0
        for (report in reports) {
            if (isReportSafe(report)) {
                res++
            }
        }

        return res
    }

    private fun isReportSafe(report: List<Int>): Boolean {
        var diff = report[1] - report[0]
        val sign = diff.sign
        if (abs(diff) > MAX_SAFE_DIFFERENCE || sign == 0) {
            return false
        }

        for (i in 2..report.lastIndex) {
            diff = report[i] - report[i - 1]
            if (diff.sign != sign || abs(diff) > MAX_SAFE_DIFFERENCE) {
                return false
            }
        }

        return true
    }

    private fun extractReports(input: String): List<List<Int>> {
        val lines = input.lines().filter { it.isNotBlank() }

        return lines.map { line ->
            line.split(' ')
                .filter { it.isNotBlank() }
                .map { it.toInt() }
        }
    }
}