package day14

import readInput

fun main() {

    fun part1(input: List<String>): Int {
        var startPair = input[0].toMutableList().also { println("Start Pair: $it") }
        val codePairs = input.filter { it.contains("->") }.map { Pair(it.split(" -> ")[0], it.split(" -> ")[1].first()) }.also { println(it) }

        (1..10).forEach { step ->
            var newStartPair = mutableListOf<Char>()
            newStartPair.add(startPair.first())
            startPair.windowed(2, 1).map { it.joinToString().replace(", ", "") }.forEach { pair ->
                val match = codePairs.first { it.first == pair }.second
                newStartPair.add(match)
                newStartPair.add(pair.last())
            }
            startPair = newStartPair
        }

        val charCount = startPair.groupingBy { it }.eachCount()

        return (charCount.values.maxOrNull()!! - charCount.values.minOrNull()!!)
    }
    //N N C B
    //N C N B C H B

    fun part2(input: List<String>): Long {
        var startPair = input[0].toMutableList().also { println("Start Pair: $it") }
        val codePairs = input.filter { it.contains("->") }.map { Pair(it.split(" -> ")[0], it.split(" -> ")[1].first()) }.also { println(it) }

        repeat((1..40).count()) {
            println("Step: $it")
            var newStartPair = mutableListOf<Char>()
            newStartPair.add(startPair.first())
            startPair.windowed(2, 1).map { it.joinToString().replace(", ", "") }.forEach { pair ->
                val match = codePairs.first { it.first == pair }.second
                newStartPair.add(match)
                newStartPair.add(pair.last())
            }
            startPair = newStartPair
        }

        val charCount = startPair.groupingBy { it }.eachCount()

        return (charCount.values.maxOrNull()!! - charCount.values.minOrNull()!!).toLong()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14", "_test")
 //   println(part1(testInput))
 //   check(part1(testInput) == 1588)
    println(part2(testInput))
 //   check(part2(testInput) == 2188189693529L)

    val input = readInput("Day14")
    println(part1(input))
//    println(part2(input))
//    check(part1(input) == 2375)
//    check(part2(input) == 0)
}

