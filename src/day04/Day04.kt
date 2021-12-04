package day04

import java.util.stream.Collectors.toList
import readInput

fun main() {

    fun createBingoCardPossibilities(input: List<String>, startPosition: Int, endPosition: Int): Map<String, MutableList<List<Int>>> {
        val bingoHorizontal = mutableListOf<List<Int>>()
        val bingoVertical = mutableListOf<List<Int>>()

        (startPosition..endPosition).forEach { i ->
            bingoHorizontal.add(input[i].split(" +".toRegex()).filter { it.isNotEmpty() }.map { it.toInt() })
        }

        for (i in 0..4) {
            bingoVertical.add(bingoHorizontal.stream().map { it.elementAt(i) }.collect(toList()))
        }

        return mapOf(Pair("Horizontal", bingoHorizontal), Pair("Vertical", bingoVertical))
    }

    fun checkBingoCard(bingoCard: Map<String, MutableList<List<Int>>>, currentBingoNumbers: List<Int>, direction: String): Boolean {
        val collect = bingoCard[direction]!!.stream().filter { currentBingoNumbers.containsAll(it) }.collect(toList())
        return collect.isNotEmpty()
    }

    fun part1(input: List<String>): Int {
        val bingoNumbers = input[0].split(",").map { it.toInt() }

        val bingoCards = mutableListOf<Map<String, MutableList<List<Int>>>>()

        for (i in 2 until input.size step 6) {
            bingoCards.add(createBingoCardPossibilities(input, i, i + 4))
        }

        for (i in bingoNumbers.indices) {
            val currentBingoNumbers = bingoNumbers.subList(0, 5 + i)

            val checkHorizontal = bingoCards.stream().filter { checkBingoCard(it, currentBingoNumbers, "Horizontal") }.findFirst()
            if (checkHorizontal.isPresent) {
                return checkHorizontal.get()["Horizontal"]!!.flatten().filter { !currentBingoNumbers.contains(it) }.sum() * currentBingoNumbers.last()
            }

            val checkVertical = bingoCards.stream().filter { checkBingoCard(it, currentBingoNumbers, "Vertical") }.findFirst()
            if (checkVertical.isPresent) {
                return checkVertical.get()["Vertical"]!!.flatten().filter { !currentBingoNumbers.contains(it) }.sum() * currentBingoNumbers.last()
            }
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        val bingoNumbers = input[0].split(",").map { it.toInt() }

        val bingoCards = mutableListOf<Map<String, MutableList<List<Int>>>>()

        for (i in 2 until input.size step 6) {
            bingoCards.add(createBingoCardPossibilities(input, i, i + 4))
        }

        val winningCards = mutableListOf<Map<String, MutableList<List<Int>>>>()

        for (i in 1..bingoNumbers.size - 5) {
            val currentBingoNumbers = bingoNumbers.subList(0, 5 + i)

            val checkHorizontal = bingoCards.stream().filter { checkBingoCard(it, currentBingoNumbers, "Horizontal") }.collect(toList())
            val checkVertical = bingoCards.stream().filter { checkBingoCard(it, currentBingoNumbers, "Vertical") }.collect(toList())

            if (checkHorizontal.size > 0 || checkVertical.size > 0) {

                if (checkHorizontal.size > 0) {
                    winningCards.addAll(checkHorizontal)
                    bingoCards.removeAll(checkHorizontal)
                }

                if (checkVertical.size > 0) {
                    winningCards.addAll(checkVertical)
                    bingoCards.removeAll(checkVertical)
                }

                if (bingoCards.size == 0) {
                    return winningCards.last()["Vertical"]!!.flatten().filter { !currentBingoNumbers.contains(it) }.sum() * currentBingoNumbers.last()
                }
            }
        }
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04", "_test")
    check(part1(testInput) == 4512)//188*24
    check(part2(testInput) == 1924)
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day04")
    check(part1(input) == 63424)
    check(part2(input) == 23541)
    println(part1(input))
    println(part2(input))
}
