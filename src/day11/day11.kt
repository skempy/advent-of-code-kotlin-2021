package day11

import java.awt.Point
import java.lang.Character.getNumericValue
import readInput

fun main() {

    var octopusGrid = mutableMapOf<Point, Int>()
    var flashedOctopus = mutableSetOf<Point>()
    var potentialOctopus = mutableSetOf<Point>()

    fun flash(it: Point) {
        val above = Point(it.x, it.y - 1)
        val below = Point(it.x, it.y + 1)
        val left = Point(it.x - 1, it.y)
        val right = Point(it.x + 1, it.y)
        val topRight = Point(it.x + 1, it.y - 1)
        val topLeft = Point(it.x - 1, it.y - 1)
        val bottomRight = Point(it.x + 1, it.y + 1)
        val bottomLeft = Point(it.x - 1, it.y + 1)

        octopusGrid[above] = octopusGrid[above]!! + 1 //above
        octopusGrid[below] = octopusGrid[below]!! + 1 //below
        octopusGrid[left] = octopusGrid[left]!! + 1 //left
        octopusGrid[right] = octopusGrid[right]!! + 1 //right

        octopusGrid[topRight] = octopusGrid[topRight]!! + 1 //Top Right
        octopusGrid[topLeft] = octopusGrid[topLeft]!! + 1 //Top Left
        octopusGrid[bottomRight] = octopusGrid[bottomRight]!! + 1 //Bottom Right
        octopusGrid[bottomLeft] = octopusGrid[bottomLeft]!! + 1 //Bottom Left

        val updatedOctopussies = listOf(
            Pair(octopusGrid[above], above),
            Pair(octopusGrid[below], below),
            Pair(octopusGrid[left], left),
            Pair(octopusGrid[right], right),
            Pair(octopusGrid[topRight], topRight),
            Pair(octopusGrid[topLeft], topLeft),
            Pair(octopusGrid[bottomRight], bottomRight),
            Pair(octopusGrid[bottomLeft], bottomLeft)
        ).filter { it.first!! > 9 }
            .filter { !flashedOctopus.contains(it.second) }

        updatedOctopussies.forEach {
            potentialOctopus.add(it.second)
        }
        flashedOctopus.add(it)
    }

    fun printGrid(octopusLength: Int, octopusDepth: Int) {
        repeat((1 .. octopusDepth).count()) { row ->
            println()
            repeat((1 .. octopusLength).count()) { column ->
                print(octopusGrid[Point(column + 1, row + 1)])
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
            printGrid(octopusLength,octopusDepth)
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
