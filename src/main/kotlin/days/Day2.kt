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

    fun countSafeReportsWithDampenerNSquaredVersion(input: String): Int {
        val reports = extractReports(input)

        var res = 0
        for (report in reports) {
            if (isReportSafe(report)) {
                res++
            } else {
                for (i in report.indices) {
                    val temp = report.toMutableList().apply { removeAt(i) }
                    if (isReportSafe(temp)) {
                        res++
                        break
                    }
                }
            }
        }

        return res
    }


    //TODO: Fix
    fun countSafeReportsWithDampener(input: String): Int {
        val reports = extractReports(input)

        var res = 0
        for (report in reports) {
            if (isReportSafeWithProblemDampener(report)) {
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

    private fun isReportSafeWithProblemDampener(report: List<Int>, droppableReports: Int = 1): Boolean {
        fun debugPrint(badIndex: Int) {
            for (i in report.indices) {
                if (i == badIndex) {
                    print('[')
                }
                print(report[i])
                if (i == badIndex) {
                    print("] ")
                } else {
                    print(' ')
                }
            }
            println()
        }

        var badLevelCount = 0
        var lCursor = 0
        var rCursor = 1
        var diff = report[rCursor] - report[lCursor]
        var sign = diff.sign

        if (diff == 0 || abs(diff) > MAX_SAFE_DIFFERENCE) {
            diff = report[rCursor] - report[lCursor]
            sign = diff.sign
            badLevelCount++
//            debugPrint(rCursor)
            rCursor = 2
        }

        while (rCursor < report.size) {
            diff = report[rCursor] - report[lCursor]
            if (diff.sign != sign || abs(diff) > MAX_SAFE_DIFFERENCE) {
                badLevelCount++
//                debugPrint(rCursor)
            } else {
                lCursor++
            }
            rCursor++
        }

//        println("report $report is ${badLevelCount <= droppableReports}, badLevelCount = $badLevelCount")

        return badLevelCount <= droppableReports
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