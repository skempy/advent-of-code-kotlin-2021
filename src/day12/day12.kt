package day12

import kotlin.streams.toList
import readInput

fun main() {

    val completedRoutes = mutableListOf<List<Pair<String, String>>>()

    fun findRoute(startRoutes: MutableList<List<Pair<String, String>>>, allRoutes: List<Pair<String, String>>, smallCaves: List<String>) {
        println("Start Routes: $startRoutes ${startRoutes.size}")
        var routes = mutableListOf<List<Pair<String, String>>>()

        startRoutes.forEach { eachRoute ->
            val nextSteps = allRoutes.filter { it.first == eachRoute.last().second }.filter { it.first != "start" || it.second != "start" }
            nextSteps.forEach { newStep ->
                val newRoute = eachRoute.plus(newStep)
                val routeList = (newRoute.map { it.first } + newRoute.map { it.second }).filter { smallCaves.contains(it) }.groupingBy { it }.eachCount()
                if (!routeList.values.contains(3)) {
                    println(newRoute)
                    routes.add(newRoute)
                }
            }
        }

        routes.forEach {
            if (it.last().second == "end") {
                completedRoutes.add(it)
            } else {
                findRoute(routes, allRoutes, smallCaves)
            }
        }

    }

    fun part1(input: List<String>): Int {
        val startingPoints = input.filter { it.contains("start") }.toSet().also { println("Starting points: $it") }
        val endPoints = input.filter { it.contains("end") }.toSet().also { println("End points: $it") }
        val middleRoutes = input.subtract(startingPoints).subtract(endPoints).also { println("Middle paths: $it") }
        val allCaves = input.stream().map { it.split("-") }.toList().flatten().distinct().toSet().also { println("All Caves: $it") }
        val smallCaves = allCaves.filter { it !in listOf("start", "end") }.filter { it.first().isLowerCase() }.also { println("Small Caves: $it") }
        val bigCaves = allCaves.filter { it !in listOf("start", "end") }.filter { it.first().isUpperCase() }.also { println("Big Caves: $it") }

        val normalRoutes = input.map { it.split("-") }.map { Pair(it.first(), it.last()) }.also { println("All Routes: $it") }
        val reversedRoutes = input.map { it.split("-") }.map { Pair(it.last(), it.first()) }.also { println("All Routes: $it") }
        val allRoutes = normalRoutes

        println()
        println()
        val startRoutes = allRoutes.filter { it.first == "start" }.map { listOf(it) }.toMutableList()

        findRoute(startRoutes, allRoutes, smallCaves)

        completedRoutes.forEach {
            println("Complete Route: $it")
        }

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

