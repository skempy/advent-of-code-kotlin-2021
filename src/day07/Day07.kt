package day07

import java.lang.Math.abs
import readInputAsIntList

fun main() {

    fun calculateCrabFuel(it: Map.Entry<Int, Int>, i: Int) = i * it.value

    fun calculateNewCrabFuel(it: Map.Entry<Int, Int>, i: Int): Int {
        var newFuelCost = 0
        (0..i).forEach { step ->
            newFuelCost += step
        }
        return newFuelCost * it.value
    }

    fun part1(input: List<Int>): Int {
        val groupedCrabs = input.groupingBy { it }.eachCount().mapValues { it.value }.toMutableMap()
        val highestPosition = groupedCrabs.keys.maxOrNull() ?: 0
        val lowestPosition = groupedCrabs.keys.minOrNull() ?: 0

        val fuelCosts = mutableMapOf<Int, Int>()

        (lowestPosition..highestPosition).forEach { matchPosition ->
            var fuel = 0
            groupedCrabs.forEach {
                when {
                    it.key > matchPosition -> fuel += calculateCrabFuel(it, (it.key - matchPosition))
                    it.key < matchPosition -> fuel += calculateCrabFuel(it, matchPosition - it.key)
                }
            }
            fuelCosts[matchPosition] = fuel
        }

        return fuelCosts.values.minOrNull() ?: 0
    }

    fun part2(input: List<Int>): Int {
        val groupedCrabs = input.groupingBy { it }.eachCount().mapValues { it.value }.toMutableMap()
        val highestPosition = groupedCrabs.keys.maxOrNull() ?: 0
        val lowestPosition = groupedCrabs.keys.minOrNull() ?: 0

        val fuelCosts = mutableMapOf<Int, Int>()

        (lowestPosition..highestPosition).forEach { matchPosition ->
            var fuel = 0
            groupedCrabs.forEach {
                when {
                    it.key > matchPosition -> fuel += calculateNewCrabFuel(it, it.key - matchPosition)
                    it.key < matchPosition -> fuel += calculateNewCrabFuel(it, matchPosition - it.key)
                }
            }
            fuelCosts[matchPosition] = fuel
        }

        return fuelCosts.values.minOrNull() ?: 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputAsIntList("Day07", "_test")
    println(part1(testInput))
    check(part1(testInput) == 37)
    println(part2(testInput))
    check(part2(testInput) == 168)

    val input = readInputAsIntList("Day07")
    println(part1(input))
    println(part2(input))
    check(part1(input) == 352997)
    check(part2(input) == 101571302)
}

