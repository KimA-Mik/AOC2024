package com.github.kima_mik

import com.github.kima_mik.days.Day1
import com.github.kima_mik.days.Day2
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
        else -> return
    }
}

fun day1() {
    val day1 = Day1()
    val input = getInput(1, 1)
        ?: exitProcess(1)

    val res1 = day1.totalDistanceBetweenLists(input)
    println("Day 1_1: $res1")

    val res2 = day1.similarityScore(input)
    println("Day 1_2: $res2")
}

fun day2() {
    val day2 = Day2()
    val input = getInput(2, 1)
        ?: exitProcess(1)

    val res1 = day2.countSafeReports(input)
    println("Day 2_1: $res1")

}

fun getInput(day: Int, puzzleInput: Int): String? {
    val file = File("inputs", "puzzle_input_${day}_$puzzleInput.txt")
    if (!file.exists()) {
        println("File ${file.absolutePath} does not exist")
        return null
    }
    return file.readText()
}