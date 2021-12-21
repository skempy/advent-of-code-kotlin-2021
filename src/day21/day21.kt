package day21

import java.lang.Character.getNumericValue
import readInput

fun main() {

    data class Player(var currentPosition: Int, var score: Int = 0, var currentRoll: Int = 0) {

        var lastDiceRolls = listOf<Int>()

        fun rollDieFrom(lastDiceRoll: Int) {
            val diceRolls = rollDiceFrom(lastDiceRoll)

            repeat((1..diceRolls.sum()).count()) {
                if (currentPosition == 10) {
                    currentPosition = 0
                }
                currentPosition += 1
            }
            score += currentPosition
        }

        fun rollDiceFrom(last: Int): List<Int> {
            val firstRoll = if (last + 1 > 100) ((last + 1) - 100) else last + 1
            val secondRoll = if (last + 2 > 100) ((last + 2) - 100) else last + 2
            val thirdRoll = if (last + 3 > 100) ((last + 3) - 100) else last + 3
            currentRoll = thirdRoll
            lastDiceRolls = listOf(firstRoll, secondRoll, thirdRoll)
            return lastDiceRolls
        }
    }

    fun part1(input: List<String>): Int {
        val player1 = Player(getNumericValue(input[0].last().code).also { println("Player1 starts at: $it") })
        val player2 = Player(getNumericValue(input[1].last().code).also { println("Player2 starts at: $it") })

        var player1Turn = true
        var gameBeingPlayed = true
        var dieCount = 0

        while (gameBeingPlayed) {
            when (player1Turn) {
                true -> {
                    player1.rollDieFrom(player2.currentRoll).also { dieCount++ }
                    if (player1.score >= 1000) gameBeingPlayed = false
                    println("Player 1 rolls ${player1.lastDiceRolls} and moves to space ${player1.currentPosition} for a total score of ${player1.score}")
                }
                false -> {
                    player2.rollDieFrom(player1.currentRoll).also { dieCount++ }
                    if (player1.score >= 1000) gameBeingPlayed = false
                    println("Player 2 rolls ${player2.lastDiceRolls} and moves to space ${player2.currentPosition} for a total score of ${player2.score}")
                }
            }
            player1Turn = !player1Turn
        }

        return player2.score * (dieCount * 3)
    }


    fun part2(input: List<String>): Int {
        return 0
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day21", "_test")
    println(part1(testInput))
    check(part1(testInput) == 739785)
//    println(part2(testInput))
//    check(part2(testInput) == 0)

    val input = readInput("Day21")
    println(part1(input))
    check(part1(input) == 551901)
//    println(part2(input))
//    check(part2(input) == 0)
}

