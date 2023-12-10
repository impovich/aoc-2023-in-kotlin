package day09

import println
import readInput


fun main() {

    fun toSequences(current: List<Int>, sequences: MutableList<List<Int>> = mutableListOf()): List<List<Int>> {
        sequences.add(current)
        if (current.all { it == 0 }) return sequences

        val sequence = current.windowed(2, 1)
            .map { it.last() - it.first() }.toList()

        return toSequences(sequence, sequences)
    }

    fun part1(input: List<String>): Int =
        input.parse()
            .sumOf { list ->
                toSequences(list).sumOf { it.last() }
            }

    fun part2(input: List<String>): Int =
        input.parse()
            .map { toSequences(it).reversed().fold(0) { acc, diff -> diff.first() - acc } }
            .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/day09/Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("/day09/Day09")
    part1(input).println() //answer: 2105961943
    part2(input).println() //answer: 1019
}

private fun List<String>.parse(): List<List<Int>> {
    return map { it.split(" ").map { it.toInt() }.toList() }.toList()
}
