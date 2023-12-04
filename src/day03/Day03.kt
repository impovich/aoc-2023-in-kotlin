package day03

import println
import readInput


private fun Char.isPart(): Boolean = !isDigit() && this != '.'

private fun Char.isGear(): Boolean = this == '*'

fun main() {

    fun toIndexedNumber(start: Int, row: String): Pair<Int, Int> {
        var left = start - 1
        var right = start + 1

        while ((left >= 0 && row[left].isDigit()) || (right < row.length && row[right].isDigit())) {
            if (row[left].isDigit()) left--
            if (row[right].isDigit()) right++
        }

        return row.substring(left + 1, right).toInt() to right - 1
    }

    fun findPartNumbers(position: Int, row: String): List<Int> {
        val left = if (position - 1 < 0) 0 else position - 1
        val right = if (position + 1 > row.length - 1) row.length - 1 else position + 1

        return buildList {
            for (i in left..right) {
                if (row[i].isDigit()) {
                    val (partNumber, endIndex) = toIndexedNumber(i, row)
                    add(partNumber)

                    if (endIndex > i) break
                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        var result = 0

        input.forEachIndexed { colIndex, line ->
            line.forEachIndexed { rowIndex, ch ->
                if (ch.isPart()) {
                    result += buildList {
                        addAll(findPartNumbers(rowIndex, input[colIndex]))
                        addAll(findPartNumbers(rowIndex, input[colIndex - 1]))
                        addAll(findPartNumbers(rowIndex, input[colIndex + 1]))
                    }.sum()
                }
            }
        }

        return result
    }

    fun part2(input: List<String>): Int {
        var result = 0

        input.forEachIndexed { colIndex, line ->
            line.forEachIndexed { rowIndex, ch ->
                if (ch.isGear()) {
                    val nums = buildList {
                        addAll(findPartNumbers(rowIndex, input[colIndex]))
                        addAll(findPartNumbers(rowIndex, input[colIndex - 1]))
                        addAll(findPartNumbers(rowIndex, input[colIndex + 1]))
                    }

                    if (nums.size == 2) {
                        result += nums.first() * nums.last()
                    }
                }
            }
        }

        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/day03/Day03_test")
    check(part1(testInput) == 4381)
    check(part2(testInput) == 467935)

    val input = readInput("/day03/Day03")
    part1(input).println() //answer: 532428
    part2(input).println() //answer: 84051670
}
