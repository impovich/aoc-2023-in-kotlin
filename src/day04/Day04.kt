package day04

import println
import readInput
import java.util.*
import kotlin.math.pow


class LotteryService(val winCards: Map<Int, LotteryCard>) {

    fun playForCards(playerCards: List<LotteryCard>): Int {
        //TODO: optimize it(get rid of queue)
        val queue = LinkedList<LotteryCard>()
        queue.addAll(playerCards)

        var cardCounter = 0
        while (queue.isNotEmpty()) {
            val lotteryCard = queue.poll()
            val matchingNumbers = intersectNumbers(lotteryCard).size

            if (matchingNumbers != 0) {
                val copies = playerCards.subList(lotteryCard.id, lotteryCard.id + matchingNumbers)
                queue.addAll(copies)
                cardCounter += copies.size
            }
        }

        return cardCounter + playerCards.size
    }

    fun playForPoints(playerCards: List<LotteryCard>): Int =
        //sumOf bug - https://youtrack.jetbrains.com/issue/KT-46360
        playerCards.map(::intersectNumbers).sumOf {
            if (it.isNotEmpty()) 2.0.pow(it.size - 1).toInt() else 0
        }

    private fun intersectNumbers(playerCard: LotteryCard): Set<Int> =
        playerCard.numbers.intersect(winCards[playerCard.id]!!.numbers.toSet())
}

data class LotteryCard(val id: Int, val numbers: List<Int>)

fun main() {

    fun part1(input: List<String>): Int {
        val (winCards, playerCard) = input.parse()

        return LotteryService(winCards.associateBy(LotteryCard::id)).playForPoints(playerCard)
    }

    fun part2(input: List<String>): Int {
        val (winCards, playerCard) = input.parse()

        return LotteryService(winCards.associateBy(LotteryCard::id)).playForCards(playerCard)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/day04/Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("/day04/Day04")
    part1(input).println() //answer: 23673
    part2(input).println() //answer: 12263631
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
