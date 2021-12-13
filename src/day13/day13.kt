package day13

import java.awt.Point
import kotlin.math.abs
import kotlin.streams.toList
import readInput

fun main() {

    fun yFold(whereToFold: Int, dots: MutableSet<Point>): MutableSet<Point> {
        val dotsToBeMoved = dots.stream().filter { it.y > whereToFold }.toList()
        val newDots = dotsToBeMoved.map { Point(it.x, whereToFold - (abs(whereToFold - it.y))) }

        val allDots = mutableSetOf<Point>().also {
            it.addAll(dots.stream().filter { it.y < whereToFold }.toList())
            it.addAll(newDots)
        }

        return allDots
    }

    fun xFold(whereToFold: Int, dots: MutableSet<Point>): MutableSet<Point> {
        val dotsToBeMoved = dots.stream().filter { it.x > whereToFold }.toList()
        val newDots = dotsToBeMoved.map { Point(whereToFold - (abs(whereToFold - it.x)), it.y) }

        val allDots = mutableSetOf<Point>().also {
            it.addAll(dots.stream().filter { it.x < whereToFold }.toList())
            it.addAll(newDots)
        }

        return allDots
    }

    fun part1(input: List<String>): Int {
        val dots = input.stream().filter { it.isNotBlank() }.filter { it.first().isDigit() }.map { Point(it.split(",")[0].toInt(), it.split(",")[1].toInt()) }.toList()
        val folds = input.stream().filter { it.isNotBlank() }.filter { it.first().isLowerCase() }.map { it.replace("fold along ", "") }.map { Pair(it.split("=")[0], it.split("=")[1].toInt()) }.toList()
        val firstFold = folds.first()

        var allDots = mutableSetOf<Point>()
        allDots.addAll(dots)

        if (firstFold.first == "y") {
            allDots = yFold(firstFold.second, allDots)
        } else {
            allDots = xFold(firstFold.second, allDots)
        }

        return allDots.size
    }

    fun part2(input: List<String>): Int {
        val dots = input.stream().filter { it.isNotBlank() }.filter { it.first().isDigit() }.map { Point(it.split(",")[0].toInt(), it.split(",")[1].toInt()) }.toList()
        val folds = input.stream().filter { it.isNotBlank() }.filter { it.first().isLowerCase() }.map { it.replace("fold along ", "") }.map { Pair(it.split("=")[0], it.split("=")[1].toInt()) }.toList()

        var allDots = mutableSetOf<Point>()
        allDots.addAll(dots)

        folds.forEach { fold ->
            if (fold.first == "y") {
                allDots = yFold(fold.second, allDots)
            } else {
                allDots = xFold(fold.second, allDots)
            }
        }

        println(allDots.size)

        repeat((0..5).count()) { column ->
            val pointsToPlot = allDots.filter { it.y == column }.map { it.x }.toList()
            println()
            repeat((0 until 100).count()) { hit ->
                if (pointsToPlot.contains(hit)) {
                    print("X")
                } else {
                    print(" ")
                }
            }
        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13", "_test")
//    println(part1(testInput))
//    check(part1(testInput) == 17)
//    println(part2(testInput))
//    check(part2(testInput) == 0)

    val input = readInput("Day13")
//    println(part1(input))
    println(part2(input))
//    check(part1(input) == 0)
//    check(part2(input) == 0)

}

