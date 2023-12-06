package day06

import println
import readInput


fun main() {

    fun Pair<Long, Long>.numOfWaysToWin(): Int =
        let { (time, distance) ->
            (0..time).map { i -> if ((time - i) * i > distance) 1 else 0 }.sum()
        }

    fun part1(input: List<String>): Int =
        input.parse()
            .map { it.numOfWaysToWin() }
            .reduce { acc, i -> acc * i }

    fun part2(input: List<String>): Int =
        input.parse()
            .reduce { acc, curr -> "${acc.first}${curr.first}".toLong() to "${acc.second}${curr.second}".toLong() }
            .numOfWaysToWin()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/day06/Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("/day06/Day06")
    part1(input).println() //answer: 32076
    part2(input).println() //answer: 34278221
}

private fun List<String>.parse(): List<Pair<Long, Long>> {
    val time = this[0].substringAfter(":").trim().split("\\s+".toRegex()).map { it.toLong() }
    val distance = this[1].substringAfter(":").trim().split("\\s+".toRegex()).map { it.toLong() }

    return time.zip(distance).toList()
}
