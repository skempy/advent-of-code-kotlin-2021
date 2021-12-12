package day11

import java.awt.Point
import java.lang.Character.getNumericValue
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        var octopusGrid = mutableMapOf<Point, Int>()

        val octopusList = input.map { it.toCharArray().toList().map { getNumericValue(it.code) } }

        repeat((octopusList.indices).count()) { row ->
            repeat((octopusList[row].indices).count()) { column ->
                octopusGrid[Point(row + 1, column + 1)] = octopusList[row][column]
            }
        }

        octopusGrid.map {
            if (it.value == 9) {
                println("Flash at: ${it.key.x},${it.key.y}")
            }
        }

        println(octopusList)
        println(octopusGrid)

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11", "_test")
    println(part1(testInput))
//    check(part1(testInput) == 0)
//    println(part2(testInput))
//    check(part2(testInput) == 0)

    val input = readInput("Day11")
//    println(part1(input))
//    println(part2(input))
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}

data class DumbOctopus(var energy: Int, val position: Int) {
    fun increase() {
        energy += 1
    }
}

