package day07

import println
import readInput

private val cardToStrengthMapping = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A')
    .mapIndexed { index, c -> c to index + 1 }.toMap()

data class Card(val hand: String, val rank: Int, val bid: Int) : Comparable<Card> {

    override fun compareTo(other: Card): Int {
        if (rank - other.rank != 0) return rank - other.rank

        for ((card, otherCard) in hand.zip(other.hand)) {
            val thisCardStrength = cardToStrengthMapping[card]!!
            val otherCardStrength = cardToStrengthMapping[otherCard]!!

            if (thisCardStrength > otherCardStrength) return 1
            if (thisCardStrength < otherCardStrength) return -1
        }

        return 0
    }
}

fun main() {

    fun part1(input: List<String>): Int =
        input.parse()
            .sorted()
            .mapIndexed { index, card -> (index + 1) * card.bid }
            .sum()

    fun part2(input: List<String>): Int =
        input.parse()
            .sorted()
            .mapIndexed { index, card -> (index + 1) * card.bid }
            .sum()


    // test if implementation meets criteria from the description, like:
    val testInput = readInput("/day07/Day07_test")
    check(part1(testInput) == 6440)
    //check(part2(testInput) == 5905)

    val input = readInput("/day07/Day07")
    part1(input).println() //answer: 253954294
}

private fun List<String>.parse(): List<Card> =
    asSequence().map {
        val split = it.split(" ")
        val hand = split.first()
        val bid = split.last().toInt()
        Card(hand, rank(hand), bid)
    }.toList()

fun rank(hand: String): Int {
    val cardsFreq = mutableMapOf<Char, Int>()
    for (i in hand.indices) {
        val card = hand[i]
        cardsFreq[card] = cardsFreq.getOrDefault(card, 0) + 1
    }

    return when (cardsFreq.size) {
        1 -> 7
        2 -> if (cardsFreq.values.count { it == 4 } == 1) 6 else 5
        3 -> if (cardsFreq.values.count { it == 1 } == 2) 4 else 3
        4 -> 2
        5 -> 1
        else -> throw IllegalStateException("BOOM!")
    }
}
