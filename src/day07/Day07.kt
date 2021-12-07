package day07

import readInputAsIntList

fun main() {

    fun part1(input: List<Int>): Int {
        val groupedCrabs = input.groupingBy { it }.eachCount().mapValues { it.value }.toMutableMap()
        val highestPosition = groupedCrabs.keys.maxOrNull() ?: 0
        val lowestPosition = groupedCrabs.keys.minOrNull() ?: 0

        val fuelCosts = mutableMapOf<Int, Int>()

        (lowestPosition..highestPosition).forEach { matchPosition ->
            //println("List of Crabs: $groupedCrabs at position: $matchPosition")
            var fuel = 0
            groupedCrabs.forEach {
                when {
                    it.key > matchPosition -> fuel += (it.key - matchPosition) * it.value
                    it.key < matchPosition -> fuel += (matchPosition - it.key) * it.value
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
                    it.key > matchPosition -> {
                        fuel += run {
                            var newFuelCost = 0
                            (0..it.key - matchPosition).forEach { step ->
                                newFuelCost += step
                            }
                            newFuelCost
                        } * it.value
                    }
                    it.key < matchPosition -> fuel += run {
                        var newFuelCost = 0
                        (0..matchPosition - it.key).forEach { step ->
                            newFuelCost += step
                        }
                        newFuelCost
                    } * it.value
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

