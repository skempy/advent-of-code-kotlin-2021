package day10

import kotlin.streams.toList
import readInput

fun main() {

    fun String.removePairsNextToEachOther(): String {
        val simplePairs = mapOf(
            "{}" to "",
            "<>" to "",
            "[]" to "",
            "()" to ""
        )
        var str = this
        while (simplePairs.keys.any { it in str }) simplePairs.forEach { str = str.replace(it.key, it.value) }
        return str
    }

    fun String.flipReverseIt(): String {
        val reversed = mapOf(
            '{' to '}',
            '[' to ']',
            '<' to '>',
            '(' to ')'
        )
        var str = this
        reversed.forEach { str = str.replace(it.key, it.value) }
        return str
    }

    fun hasCorrectNextP(nextP: Char) = listOf('{', '(', '<', '[').contains(nextP)

    fun part1(input: List<String>): Int {
        val keys = mapOf(
            '}' to 1197,
            ']' to 57,
            '>' to 25137,
            ')' to 3
        )
        val x = input.stream()
            .map {
                it.removePairsNextToEachOther()
                    .zipWithNext { _, nextP ->
                        if (!hasCorrectNextP(nextP)) keys.getOrDefault(nextP, nextP) else 0
                    }
                    .toList().firstOrNull { it != 0 }
            }
            .toList()
            .filterNotNull() as List<Int>

        return x.sum()
    }


    fun calculateScore(chunk: String): Long {
        val keys = mapOf(
            '}' to 3L,
            ']' to 2L,
            '>' to 4L,
            ')' to 1L
        )
        var value = 0L
        chunk.forEach {
            value *= 5L
            value += keys[it]!!
        }
        return value
    }

    fun part2(input: List<String>): Long {

        val incompleteCodes = input.stream()
            .map { inputLine ->
                inputLine.removePairsNextToEachOther()
                    .zipWithNext { _, nextP ->
                        if (!hasCorrectNextP(nextP)) "INCORRECT" else inputLine.removePairsNextToEachOther()
                    }
                    .toList()
                    .distinct()
            }.toList()
            .filter { !it.contains("INCORRECT") }
            .toList()
            .flatten()


        return incompleteCodes
            .map { flipped -> flipped.reversed().flipReverseIt() }
            .stream()
            .map { chunk -> calculateScore(chunk) }
            .toList()
            .sorted<Long>()
            .elementAt(incompleteCodes.size / 2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10", "_test")
    println(part1(testInput))
    check(part1(testInput) == 26397)
    println(part2(testInput))
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    check(part1(input) == 216297)
    println(part2(input))
    check(part2(input) == 2165057169)
}

