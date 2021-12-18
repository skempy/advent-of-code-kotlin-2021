package day15

import day15.Graph.PointOnGraph
import java.awt.Point
import java.lang.Character.getNumericValue
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        var x = 1
        var y = 1
        val points = input.map { line ->
            line.toCharArray().toList().map {
                PointOnGraph(Point(x, y), getNumericValue(it.code)).also {
                    x += 1
                }
            }.also {
                y += 1
                x = 1
            }
        }.flatten()

        val graph = Graph(points)
        val pointA = PointOnGraph(Point(0, 0), 1)
        val pointB = PointOnGraph(Point(0, 1), 5)
        val weight = graph.findShortestPath(pointA, pointB)
        println(weight)

        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15", "_test")
    println(part1(testInput))
//    check(part1(testInput) == 0)
//    println(part2(testInput))
//    check(part2(testInput) == 0)

    val input = readInput("Day15")
//    println(part1(input))
//    println(part2(input))
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}

data class Graph(val points: List<PointOnGraph>) {

    fun findShortestPath(pointA: PointOnGraph, pointB: PointOnGraph): Int {
        return pointA.calculatePath(pointB)
    }

    data class PointOnGraph(val coord: Point, val weight: Int) {
        fun calculatePath(point: PointOnGraph): Int {
            return point.weight + weight
        }
    }
}

