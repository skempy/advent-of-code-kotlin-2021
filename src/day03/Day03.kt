package day03

import java.util.stream.Collectors.toList
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        var gammaString = ""
        var epilsonString = ""

        (0 until input.first().length).forEach { i ->
            val countOfGamma = input.stream().map { it.substring(i, i + 1).toInt() }.collect(toList()).sum()

            if (countOfGamma > input.size / 2) {
                gammaString += "1"
                epilsonString += "0"
            } else {
                gammaString += "0"
                epilsonString += "1"
            }
        }

        val gamma = Integer.parseInt(gammaString, 2)
        val epsilon = Integer.parseInt(epilsonString, 2)

        return gamma * epsilon
    }

    fun looper(input: List<String>, startPosition: Int, binaryFlip: Boolean): Int {
        val numberOf1 = input.stream().filter { it.substring(startPosition, startPosition + 1).toInt() == 1 }.collect(toList())
        val numberOf0 = input.stream().filter { it.substring(startPosition, startPosition + 1).toInt() == 0 }.collect(toList())

        return when {
            numberOf1.size > numberOf0.size && binaryFlip -> looper(numberOf1, startPosition + 1, binaryFlip)
            numberOf1.size < numberOf0.size && binaryFlip -> looper(numberOf0, startPosition + 1, binaryFlip)
            numberOf0.size > numberOf1.size -> looper(numberOf1, startPosition + 1, binaryFlip)
            numberOf0.size < numberOf1.size -> looper(numberOf0, startPosition + 1, binaryFlip)
            binaryFlip && (numberOf1.size > 1) -> looper(numberOf1, startPosition + 1, binaryFlip)
            !binaryFlip && (numberOf0.size > 1) -> looper(numberOf0, startPosition + 1, binaryFlip)
            else -> if (binaryFlip) {
                Integer.parseInt(numberOf1.first(), 2)
            } else {
                Integer.parseInt(numberOf0.first(), 2)
            }
        }
    }

    fun part2(input: List<String>): Int {
        val oxygen = looper(input, 0, true)
        val co2 = looper(input, 0, false)
        return oxygen * co2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03", "_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    check(part1(input) == 3969000)
    check(part2(input) == 4267809)
    println(part1(input))
    println(part2(input))
}
