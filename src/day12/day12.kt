package day12

import kotlin.streams.toList
import readInput

fun main() {


    fun detectDoubleSmallCaves(route: String): Boolean {
        val caves = route.split("-").filter { it.first().isLowerCase() }
        val groupedSmallCaves = caves.groupingBy { it }.eachCount().filter { it.value > 1 }
        if (groupedSmallCaves.isNotEmpty()) {
            return false
        }
        return true
    }

    fun findNextStep(unknownRoutes: List<String>, input: List<String>): List<String> {
        var completedRoutes = mutableListOf<String>()

        unknownRoutes.map { route ->
            val lastCave = route.last().toString()
            val routes = input
                .asSequence()
                .filter { it.takeLast(3) != "end" }
                .filter { it.contains(lastCave) }
                .filter { it != route }
                .filter { detectDoubleSmallCaves(route) }
                .map { if (it.first().toString() != lastCave) it.reversed() else it }
                .map { it.replace(lastCave, route) }
                .toList()

            val numberOfOpenRoutes = routes.stream().filter { !it.contains("end") }.toList().size

            if (numberOfOpenRoutes == 0) {
                completedRoutes.addAll(routes)
            } else {
                println(routes)
                findNextStep(routes, input.filter { !it.contains("start") })
            }
        }
        return completedRoutes
    }

    fun part1(input: List<String>): Int {
        val startingPoint = input.filter { it.contains("start") }
        val unknownPaths = input.filter { !it.contains("start") }
        val left = input.stream().map { it.split("-")[0] }.toList().distinct()
        val right = input.stream().map { it.split("-")[1] }.toList().distinct()
        val allCaves = (left + right).distinct()
        val smallCaves = allCaves.filter { it !in listOf("start", "end") }.filter { it.first().isLowerCase() }
        val bigCaves = allCaves.filter { it !in listOf("start", "end") }.filter { it.first().isUpperCase() }

        val routes = findNextStep(startingPoint, input)

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

