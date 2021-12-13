package day11

import java.awt.Point
import java.lang.Character.getNumericValue
import readInput

fun main() {

    var octopusGrid = mutableMapOf<Point, Int>()
    var flashedOctopus = mutableSetOf<Point>()
    var flashes = 0

    fun increaseEnergy(it: Point): Point {
        if (octopusGrid.contains(it)) {
            octopusGrid[it] = octopusGrid[it]!! + 1
            if (octopusGrid[it]!! > 9 && !flashedOctopus.contains(it)) {
                flashes += 1
                return it
            }
        }
        return Point()
    }

    fun flash(it: Point) {
        flashedOctopus.add(it)
        Point(it.x, it.y - 1).also { if (increaseEnergy(it) != Point()) flash(it) } //above
        Point(it.x, it.y + 1).also { if (increaseEnergy(it) != Point()) flash(it) } //below
        Point(it.x - 1, it.y).also { if (increaseEnergy(it) != Point()) flash(it) } //left
        Point(it.x + 1, it.y).also { if (increaseEnergy(it) != Point()) flash(it) } //right
        Point(it.x + 1, it.y - 1).also { if (increaseEnergy(it) != Point()) flash(it) } //topRight
        Point(it.x - 1, it.y - 1).also { if (increaseEnergy(it) != Point()) flash(it) } //topLeft
        Point(it.x + 1, it.y + 1).also { if (increaseEnergy(it) != Point()) flash(it) } //bottomRight
        Point(it.x - 1, it.y + 1).also { if (increaseEnergy(it) != Point()) flash(it) } //bottomLeft
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
        flashes = 0
        val octopusLength = input[0].length
        val octopusDepth = input.size

        val octopusList = input.map { it.toCharArray().toList().map { getNumericValue(it.code) } }

        repeat((octopusList.indices).count()) { row ->
            repeat((octopusList[row].indices).count()) { column ->
                octopusGrid[Point(row + 1, column + 1)] = octopusList[row][column]
            }
        }

        (1..100).forEach {

            octopusGrid.map {
                octopusGrid[it.key] = it.value + 1

                if (it.value > 9 && !flashedOctopus.contains(it.key)) {
                    flash(it.key)
                    flashes += 1
                }
            }

            octopusGrid.map {
                if (flashedOctopus.contains(it.key)) {
                    octopusGrid[it.key] = 0
                }
            }
            //printGrid(octopusLength, octopusDepth)
            //println()
            flashedOctopus.clear()
        }
        return flashes
    }

    fun part2(input: List<String>): Int {
        var count = 0

        val octopusList = input.map { it.toCharArray().toList().map { getNumericValue(it.code) } }

        repeat((octopusList.indices).count()) { row ->
            repeat((octopusList[row].indices).count()) { column ->
                octopusGrid[Point(row + 1, column + 1)] = octopusList[row][column]
            }
        }

        while (octopusGrid.values.sum() != 0) {
            count++
            octopusGrid.map {
                octopusGrid[it.key] = it.value + 1

                if (it.value > 9 && !flashedOctopus.contains(it.key)) {
                    flash(it.key)
                    flashes += 1
                }
            }

            octopusGrid.map {
                if (flashedOctopus.contains(it.key)) {
                    octopusGrid[it.key] = 0
                }
            }
            flashedOctopus.clear()
        }
        println(count)
        return count
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11", "_test")
    part1(testInput)
    check(part1(testInput) == 1656)
    println(part2(testInput))
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
    check(part1(input) == 1694)
    check(part2(input) == 346)
}
