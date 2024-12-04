package com.github.kima_mik

import com.github.kima_mik.days.Day1
import com.github.kima_mik.days.Day2
import com.github.kima_mik.days.Day3
import com.github.kima_mik.days.Day4
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 1) {
        return
    }
    val day = args[0].toIntOrNull() ?: return

    when (day) {
        1 -> day1()
        2 -> day2()
        3 -> day3()
        4 -> day4()
        else -> return
    }
}

fun day1() {
    val day1 = Day1()
    val input = getInput(1)
        ?: exitProcess(1)

    val res1 = day1.totalDistanceBetweenLists(input)
    println("Day 1_1: $res1")

    val res2 = day1.similarityScore(input)
    println("Day 1_2: $res2")
}

fun day2() {
    val day2 = Day2()
    val input = getInput(2)
        ?: exitProcess(1)

    val res1 = day2.countSafeReports(input)
    println("Day 2_1: $res1")

    val res2 = day2.countSafeReportsWithDampenerNSquaredVersion(input)
    println("Day 2_2: $res2")
}

fun day3() {
    val day3 = Day3()
    val input = getInput(3)
        ?: exitProcess(1)

    val res1 = day3.extractMultiplyFromCorruptedMemory(input)
    println("Day 3_1: $res1")

    val res2 = day3.damnItPuzzle2(input)
    println("Day 3_2: $res2")
}

fun day4() {
    val day4 = Day4()
    val input = getInput(4)
        ?: exitProcess(1)

    val res1 = day4.puzzle1(input)
    println("Day 4_1: $res1")
}

fun getInput(day: Int, puzzleInput: Int? = null): String? {
    val filename = buildString {
        append("puzzle_input_")
        append(day)
        puzzleInput?.let {
            append("_")
            append(it)
        }
        append(".txt")
    }

    val file = File("inputs", filename)
    if (!file.exists()) {
        println("File ${file.absolutePath} does not exist")
        return null
    }
    return file.readText()
}