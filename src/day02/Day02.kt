package day02

import println
import readInput

enum class CubeColor { RED, GREEN, BLUE }

data class Round(val cubes: Map<CubeColor, Int>) {
    fun isValid(target: Round): Boolean =
        cubes.all {
            ((cubes[CubeColor.RED] ?: 0) <= target.cubes[CubeColor.RED]!!)
                .and((cubes[CubeColor.GREEN] ?: 0) <= target.cubes[CubeColor.GREEN]!!)
                .and((cubes[CubeColor.BLUE] ?: 0) <= target.cubes[CubeColor.BLUE]!!)
        }

    fun getBy(color: CubeColor): Int = cubes[color] ?: 0
}

data class Game(val id: Int, val rounds: List<Round>)

fun main() {
    val target = Round(
        mapOf(CubeColor.RED to 12, CubeColor.GREEN to 13, CubeColor.BLUE to 14)
    )

    fun part1(input: List<String>): Int =
        input.map(String::toGame)
            .filter { game -> game.rounds.all { it.isValid(target) } }
            .sumOf { it.id }

    fun part2(input: List<String>): Int =
        input.map(String::toGame)
            .sumOf {
                it.rounds.maxOf { round -> round.getBy(CubeColor.RED) }
                    .times(it.rounds.maxOf { round -> round.getBy(CubeColor.GREEN) })
                    .times(it.rounds.maxOf { round -> round.getBy(CubeColor.BLUE) })
            }

    // test if implementation meets criteria from the description, like:
    check(part1(readInput("/day02/Day02_test")) == 8)
    check(part2(readInput("/day02/Day02_test")) == 2286)

    val input = readInput("/day02/Day02")
    part1(input).println() //answer: 2256
    part2(input).println() //answer: 74229
}

private fun String.toGame(): Game {
    val id = substringBefore(":").substringAfter(" ").toInt()
    val rounds = substringAfter(":").toRounds();

    return Game(id, rounds)
}

private fun String.toRounds(): List<Round> =
    split(";").asSequence()
        .map(String::trim)
        .map(String::toRound)
        .toList()

fun String.toRound(): Round =
    Round(
        split(",")
            .asSequence()
            .map(String::trim)
            .map {
                val cubesNumber = """\d+""".toRegex().find(it)!!.value.toInt()
                val color = CubeColor.valueOf(it.substring(2).trim().uppercase())
                color to cubesNumber
            }.toMap()
    )
