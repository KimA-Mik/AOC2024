package com.github.kima_mik

import com.github.kima_mik.days.*
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 1) {
        return
    }
    val dayNum = args[0].toIntOrNull() ?: return

    val day = when (dayNum) {
        1 -> Day1()
        2 -> Day2()
        3 -> Day3()
        4 -> Day4()
        5 -> Day5()
        6 -> Day6()
        7 -> Day7()
        8 -> Day8()
        else -> {
            println("Unknown day $dayNum")
            return
        }
    }
    runDay(day)
}

fun runDay(day: AOCDay) {
    val num = day.day
    val input = getInput(num)
        ?: exitProcess(1)

    println("Day ${num}_1: ${day.puzzle1(input)}")
    println("Day ${num}_2: ${day.puzzle2(input)}")
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
        println("Expected $filename with input data")
        return null
    }
    return file.readText()
}