package day08

import println
import readInput

enum class Direction {
    LEFT, RIGHT;

    companion object {

        fun from(str: Char): Direction =
            when (str) {
                'L' -> LEFT
                'R' -> RIGHT
                else -> throw IllegalStateException("Unknown direction: $str")
            }
    }
}

fun main() {

    fun calculateSteps(
        instructions: List<Direction>,
        map: Map<String, Pair<String, String>>,
        from: String,
        to: String
    ): Int {
        var curr = from

        var steps = 0
        while (curr != to) {
            curr = instructions[steps % instructions.size].let { direction ->
                val (left, right) = map[curr]!!
                when (direction) {
                    Direction.LEFT -> left
                    Direction.RIGHT -> right
                }
            }

            steps++
        }

        return steps
    }

    fun part1(input: List<String>): Int {
        val (instructions, map) = input.parse()

        return calculateSteps(instructions, map, "AAA", "ZZZ")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/day08/Day08_test")
    val testInput2 = readInput("/day08/Day08_test_2")
    check(part1(testInput) == 2)
    check(part1(testInput2) == 6)

    val input = readInput("/day08/Day08")
    part1(input).println() //answer: 24253
}

private fun List<String>.parse(): Pair<List<Direction>, Map<String, Pair<String, String>>> {
    val instructions = first().toCharArray().map(Direction::from)
    val map = asSequence().drop(2)
        .map {
            val key = it.substring(0, 3)
            val left = it.substring(7, 10)
            val right = it.substring(12, it.length - 1)
            key to Pair(left, right)
        }.toMap()

    return instructions to map
}
