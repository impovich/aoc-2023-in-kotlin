package day04

import println
import readInput


class LotteryPointsService(val winCards: Map<Int, LotteryCard>) {

    fun getPoints(playerCards: List<LotteryCard>): Int =
        playerCards.map(::getPoints).sum()

    private fun getPoints(playerCard: LotteryCard): Int =
        winCards[playerCard.id]?.let { winCard ->
            playerCard.numbers.intersect(winCard.numbers).let {
                if (it.isNotEmpty()) it.fold(0) { acc, _ -> if (acc == 0) 1 else acc * 2 } else 0
            }
        } ?: 0
}

data class LotteryCard(val id: Int, val numbers: List<Int>)

fun main() {

    fun part1(input: List<String>): Int {
        val (winCards, playerCard) = input.parse()

        return LotteryPointsService(winCards.associateBy(LotteryCard::id)).getPoints(playerCard)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/day04/Day04_test")
    check(part1(testInput) == 13)

    val input = readInput("/day04/Day04")
    part1(input).println() //answer: 23673
}

private fun Iterable<String>.parse(): Pair<List<LotteryCard>, List<LotteryCard>> {
    fun String.toNumbers(): List<Int> =
        trim().splitToSequence(" ").filter(String::isNotBlank).map(String::toInt).toList()

    return asSequence().map {
        val semicolonIndex = it.indexOf(":")
        val id = it.substring(5, semicolonIndex).trim().toInt()
        val split = it.substring(semicolonIndex + 1).split("|")
        val winningNumbers = split.first().toNumbers()
        val playerNumbers = split.last().toNumbers()

        LotteryCard(id, winningNumbers) to LotteryCard(id, playerNumbers)
    }.fold(Pair(mutableListOf<LotteryCard>(), mutableListOf<LotteryCard>())) { acc, (winCard, playerCard) ->
        acc.also {
            acc.first.add(winCard)
            acc.second.add(playerCard)
        }
    }
}
