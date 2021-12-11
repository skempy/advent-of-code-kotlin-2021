package day08

import kotlin.streams.toList
import readInput

fun main() {

    fun part1(input: List<String>): Int {
        return input.stream().map { it.split("|")[1].trim().split(" ") }.toList().flatten().count { listOf(2, 3, 4, 7).contains(it.length) }
    }

    fun part2(input: List<String>): Int {

        val listOfTotal = mutableListOf<Int>()

        input.map { inputLine ->

            val part1 = inputLine.split("|")[0].trim().split(" ")
            val part2 = inputLine.split("|")[1].trim().split(" ")

            val knownSegments = mapOf(
                1 to part1.first { it.length == 2 },
                7 to part1.first { it.length == 3 },
                4 to part1.first { it.length == 4 },
                8 to part1.first { it.length == 7 }
            )

            println(knownSegments)

            val unknownSegments = part1.filter { !knownSegments.values.contains(it) }
            val fiveTwoThree = unknownSegments.filter { it.length == 5 }

            val a = knownSegments[7]!!.filter { !knownSegments[1]!!.contains(it) }
            val fg = knownSegments[4]!!.filter { !knownSegments[1]!!.contains(it) }
            val d = fiveTwoThree.map { it.filter { !(a + fg + knownSegments[1]!!).contains(it) } }.toList().first { it.length == 1 }
            val e = knownSegments[8]!!.filter { !(a + knownSegments[1]!! + fg + d).contains(it) }
            val b = fiveTwoThree.first { it.contains(e) }.filter { !(a + d + e + fg).contains(it) }
            val c = knownSegments[1]!!.filter { !(b).contains(it) }
            val g = fiveTwoThree.first { it.contains(e) }.filter { !(a + b + d + e).contains(it) }
            val f = fg.filter { !(g).contains(it) }

            println("a: $a")
            println("b: $b")
            println("c: $c")
            println("d: $d")
            println("e: $e")
            println("g: $g")
            println("f: $f")

            val numbers = mapOf(
                0 to (a + b + c + d + e + f).toCharArray().sorted(),
                1 to (b + c).toCharArray().sorted(),
                2 to (a + b + d + e + f).toCharArray().sorted(),
                3 to (a + b + c + d + g).toCharArray().sorted(),
                4 to (b + c + f + g).toCharArray().sorted(),
                5 to (a + c + d + f + g).toCharArray().sorted(),
                6 to (a + c + d + e + f + g).toCharArray().sorted(),
                7 to (a + b + c).toCharArray().sorted(),
                8 to (a + b + c + d + e + f + g).toCharArray().sorted(),
                9 to (a + b + c + d + f + g).toCharArray().sorted()
            )
            println(numbers)
            println(part2)

            val lol = part2.stream().map { segment ->
                println(segment)
                numbers.entries.first {
                    println(it.value)
                    it.value == segment.toCharArray().sorted()
                }.key.toString()
            }.toList()

            var value = ""
            lol.forEach {
                value += it
            }
            listOfTotal.add(value.toInt())
        }
        return listOfTotal.sum()
    }

// test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08", "_test")
//    println(part1(testInput))
//    check(part1(testInput) == 26)
//    println(part2(testInput))
//    check(part2(testInput) == 0)
//
    val input = readInput("Day08")
//    println(part1(input))
    println(part2(input))
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}

