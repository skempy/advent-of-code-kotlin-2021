package day09

import java.lang.Character.getNumericValue
import readInput

fun main() {

    fun findLowPoint(top: List<Int>, middle: List<Int>, bottom: List<Int>, number: Int): Boolean {
        val centre = middle.getOrElse(number) { 0 }
        val above = top.getOrElse(number) { centre + 1 }
        val below = bottom.getOrElse(number) { centre + 1 }
        val left = middle.getOrElse(number - 1) { centre + 1 }
        val right = middle.getOrElse(number + 1) { centre + 1 }

        val lowerPoints = listOf(above, below, left, right).filter { centre >= it }.size
        return lowerPoints == 0
    }

    fun part1(input: List<String>): Int {
        val intLists = input.map { it.toCharArray().toList().map { getNumericValue(it.code) } }

        val smokeMap = mutableMapOf<Int, List<Int>>()

        repeat((intLists.indices).count()) {
            smokeMap[it] = intLists[it]
        }

        var lowPoints = mutableListOf<Int>()

        repeat((smokeMap.keys.indices).count()) { key ->
            val top = smokeMap.getOrElse(key - 1) { emptyList() }
            val middle = smokeMap.getOrElse(key) { emptyList() }
            val bottom = smokeMap.getOrElse(key + 1) { emptyList() }

            repeat((smokeMap[key]!!.indices).count()) {
                if (findLowPoint(top, middle, bottom, it)) {
                    lowPoints.add(middle[it])
                }
            }
        }

        return lowPoints.sumOf { it + 1 }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09", "_test")
    println(part1(testInput))
    check(part1(testInput) == 15)
//    println(part2(testInput))
//    check(part2(testInput) == 0)

    val input = readInput("Day09")
    println(part1(input))
    check(part1(input) == 530)
//   println(part2(input))
//    check(part2(input) == 0)
}

