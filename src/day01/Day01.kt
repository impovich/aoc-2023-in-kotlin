package day01

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int = input.map {
        val first = it.find(Char::isDigit)
        val second = it.findLast(Char::isDigit)
        "$first$second".toInt()
    }.reduce{acc, i -> acc + i}

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/day01/Day01_test")
    check(part1(testInput) == 142)

    val input = readInput("/day01/Day01")
    part1(input).println()
    //part2(input).println()
}
