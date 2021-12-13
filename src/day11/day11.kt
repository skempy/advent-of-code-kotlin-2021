package day11

import java.awt.Point
import java.lang.Character.getNumericValue
import readInput

fun main() {

    var octopusGrid = mutableMapOf<Point, Int>()
    var flashedOctopus = mutableSetOf<Point>()
    var potentialOctopus = mutableSetOf<Point>()

    fun increaseEnergy(it: Point) {
        if (octopusGrid.contains(it)) {
            octopusGrid[it] = octopusGrid[it]!! + 1
            if (octopusGrid[it]!! > 9 && !flashedOctopus.contains(it)) {
                potentialOctopus.add(it)
            }
        }
    }

    fun flash(it: Point) {
        Point(it.x, it.y - 1).also { increaseEnergy(it) } //above
        Point(it.x, it.y + 1).also { increaseEnergy(it) } //below
        Point(it.x - 1, it.y).also { increaseEnergy(it) } //left
        Point(it.x + 1, it.y).also { increaseEnergy(it) } //right
        Point(it.x + 1, it.y - 1).also { increaseEnergy(it) } //topRight
        Point(it.x - 1, it.y - 1).also { increaseEnergy(it) } //topLeft
        Point(it.x + 1, it.y + 1).also { increaseEnergy(it) } //bottomRight
        Point(it.x - 1, it.y + 1).also { increaseEnergy(it) } //bottomLeft

        flashedOctopus.add(it)
    }

    fun printGrid(octopusLength: Int, octopusDepth: Int) {
        (1..octopusDepth).forEach { row ->
            println()
            (1..octopusLength).forEach { column ->
                print(octopusGrid[Point(row, column)])
            }
        }
    }

    fun part1(input: List<String>): Int {
        val octopusLength = input[0].length
        val octopusDepth = input.size

        val octopusList = input.map { it.toCharArray().toList().map { getNumericValue(it.code) } }

        repeat((octopusList.indices).count()) { row ->
            repeat((octopusList[row].indices).count()) { column ->
                octopusGrid[Point(row + 1, column + 1)] = octopusList[row][column]
            }
        }

        (0..1).forEach {
            octopusGrid.map {
                octopusGrid[it.key] = it.value + 1

                if (it.value > 9 && !flashedOctopus.contains(it.key)) {
                    println("Flash at: ${it.key.x},${it.key.y}")
                    flash(it.key)
                }
            }

            potentialOctopus.subtract(flashedOctopus).forEach { flash(it) }

            octopusGrid.map {
                if (flashedOctopus.contains(it.key)) {
                    octopusGrid[it.key] = 0
                }
            }
            printGrid(octopusLength, octopusDepth)
            println()
            flashedOctopus.clear()
            potentialOctopus.clear()
        }
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11", "_test")
    part1(testInput)
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

//34543
//40004
//50005
//40004
//34543

//
//                println("(${it.key.x},${it.key.y}), Above at: ${it.key.x},${it.key.y - 1}")
//                println("(${it.key.x},${it.key.y}),Below at: ${it.key.x},${it.key.y + 1}")
//                println("(${it.key.x},${it.key.y}),Left at: ${it.key.x - 1},${it.key.y}")
//                println("(${it.key.x},${it.key.y}),Right at: ${it.key.x + 1},${it.key.y}")
//
//                println("(${it.key.x},${it.key.y}),Top Right at: ${it.key.x + 1},${it.key.y - 1}")
//                println("(${it.key.x},${it.key.y}),Top Left at: ${it.key.x - 1},${it.key.y - 1}")
//                println("(${it.key.x},${it.key.y}),Bottom Right at: ${it.key.x + 1},${it.key.y + 1}")
//                println("(${it.key.x},${it.key.y}),Bottom Left: ${it.key.x - 1},${it.key.y + 1}")
