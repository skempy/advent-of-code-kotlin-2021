package day12

import kotlin.streams.toList
import readInput

fun main() {

    fun findNextStep(unknownRoutes: List<String>, input: List<String>): List<String> {
        var completedRoutes = mutableListOf<String>()
        val startPoint = input.filter { it.contains("start") }
        unknownRoutes.map { route ->
            val lastCave = route.last().toString()
            val routes = input
                .asSequence()
                .filter { it.takeLast(3) != "end" }
                .filter { it.contains(lastCave) }
                .filter { it != route }
                .map { if (it.first().toString() != lastCave) it.reversed() else it }
                .map { it.replace(lastCave, route) }
                .toList()
            val numberOfOpenRoutes = routes.stream().filter { !it.contains("end") }.toList().size
            val pass2 = findNextStep(routes, input.filter { !it.contains("start") })
            completedRoutes.addAll(pass2)
        }
        return completedRoutes
    }

    fun exploreCaves(unknownRoutes: List<String>, input: List<String>, smallCaves: List<String>): MutableList<List<String>> {
        var completedRoutes = mutableListOf<List<String>>()
        unknownRoutes.forEach { route ->
            val lastCave = route.last().toString()
            println(lastCave)
            completedRoutes.add(listOf(route, lastCave))
        }
        return completedRoutes
    }

    fun part1(input: List<String>): Int {
        val startingPoint = input.filter { it.contains("start") }
        val left = input.stream().map { it.split("-")[0] }.toList().distinct()
        val right = input.stream().map { it.split("-")[1] }.toList().distinct()
        val allCaves = (left + right).distinct()
        val smallCaves = allCaves.filter { it !in listOf("start", "end") }.filter { it.first().isLowerCase() }
        val bigCaves = allCaves.filter { it !in listOf("start", "end") }.filter { it.first().isUpperCase() }

        val routes = exploreCaves(startingPoint, input, smallCaves)
        //val routes = findNextStep(startingPoint, input)
        println(routes)
        return 0
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12", "_test")
    println(part1(testInput))
//    check(part1(testInput) == 0)
//    println(part2(testInput))
//    check(part2(testInput) == 0)

    val input = readInput("Day12")
//    println(part1(input))
//    println(part2(input))
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}

