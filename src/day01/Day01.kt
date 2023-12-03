package day01

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf {
            val first = it.find(Char::isDigit)
            val last = it.findLast(Char::isDigit)
            "${first}$last".toInt()
        }

    fun part2(input: List<String>): Int {
        val textDigits = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val mapping = textDigits.mapIndexed { index, s -> s to index + 1 }.toMap()
        val allDigits = ('1'..'9').map { it.toString() } + textDigits

        return input.asSequence()
            .map { it.findAnyOf(allDigits)!!.second to it.findLastAnyOf(allDigits)!!.second }
            .sumOf { (first, last)-> "${mapping.getOrDefault(first, first)}${mapping.getOrDefault(last, last)}".toInt() }
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("/day01/Day01_test")) == 142)
    check(part2(readInput("/day01/Day01_2_test")) == 281)

    val input = readInput("/day01/Day01")
    part1(input).println() // answer = 55130
    part2(input).println() // answer = 54985
}
