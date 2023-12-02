package day01

import println
import readInput

fun main() {
    fun part1(input: List<String>): Int =
        input.sumOf {
            val first = it.find(Char::isDigit)
            val second = it.findLast(Char::isDigit)
            "${first}$second".toInt()
        }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("/day01/Day01_test")) == 142)
    check(part2(readInput("/day01/Day01_2_test")) == 281)

    part1(readInput("/day01/Day01")).println() // answer = 54990
    part2(readInput("/day01/Day01_2")).println()
}
