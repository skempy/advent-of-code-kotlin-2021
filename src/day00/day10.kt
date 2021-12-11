package day00

import readInput

fun main() {

    fun part1(input: List<Int>): Int {
        return 0
    }

    fun part2(input: List<Int>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day00", "_test").map { it.toInt() }
    println(part1(testInput))
    check(part1(testInput) == 0)
    println(part2(testInput))
    check(part2(testInput) == 0)

    val input = readInput("Day00").map { it.toInt() }
    println(part1(input))
    println(part2(input))
    check(part1(input) == 0)
    check(part2(input) == 0)
}

