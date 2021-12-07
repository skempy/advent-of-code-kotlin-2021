package day06

import readInput

fun main() {

    fun calculateFishFor(input: List<String>, days: Int): Ocean {
        val ocean = Ocean(input[0].split(",").map { Fish(it.toInt()) }.toMutableList())
        for (i in 1..days) {
            ocean.day()
        }
        return ocean
    }

    fun part1(input: List<String>): Int {
        val ocean = calculateFishFor(input, 80)
        return ocean.fish.size
    }

    fun part2(input: List<String>): Long {
        val ocean = input[0].split(",").map { it.toLong() }.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()

        for (i in 0..8) {
            if (!ocean.containsKey(i.toLong())) {
                ocean[i.toLong()] = 0L
            }
        }

        for (i in 1..256) {
            val rebornFishes = ocean[0L]
            ocean.toSortedMap().forEach { (key, values) ->
                when {
                    key == 0L -> {
                        ocean[9L] = values
                        ocean[0L] = ocean[1L]!!.toLong()
                    }
                    key <= 8 -> {
                        ocean[key] = ocean[key + 1L]!!.toLong()
                    }
                }
            }
            ocean[6] = ocean[6]!!.plus(rebornFishes!!.toLong())
        }
        return ocean.filter { it.key != 9L }.values.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06", "_test")
    println(part1(testInput))
    check(part1(testInput) == 5934)
    println(part2(testInput))
    check(part2(testInput) == 26984457539)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
    check(part1(input) == 360610)
    check(part2(input) == 0L)
}

data class Ocean(var fish: MutableList<Fish>) {
    fun day() {
        val iterator = fish.listIterator()
        while (iterator.hasNext()) {
            val fish = iterator.next()
            if (fish.age == 0) {
                iterator.add(Fish(8))
            }
            fish.age()
        }
    }
}

data class Fish(var age: Int) {
    fun age() {
        if (age == 0) age = 6 else age -= 1
    }
}
