package com.github.kima_mik.days

abstract class AOCDay(val day: Int) {
    abstract fun puzzle1(input: String): Any?
    abstract fun puzzle2(input: String): Any?
}