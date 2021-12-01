package day01

import readInput

fun main() {

    fun part1(input: List<Int>): Int {
        return input.zipWithNext { lastDepth, newDepth -> if (newDepth > lastDepth) 1 else 0 }.sum()
    }

    fun part2(input: List<Int>): Int {
        return part1(input.windowed(size = 3).map { it.sum() })
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01", "_test").map { it.toInt() }
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01").map { it.toInt() }
    println(part1(input))
    println(part2(input))
}
