package day05

import readInput

fun main() {


    fun addVerticalLines(startPosition: Int, endPosition: Int, second: Int, straightLines: MutableList<Pair<Int, Int>>) {
        if (startPosition > endPosition) {
            (endPosition..startPosition)
                .forEach {
                    straightLines.add(Pair(it, second))
                }
        } else {
            (startPosition..endPosition)
                .forEach {
                    straightLines.add(Pair(it, second))
                }

        }
    }

    fun addHorizontalLines(startPosition: Int, endPosition: Int, second: Int, straightLines: MutableList<Pair<Int, Int>>) {
        if (startPosition > endPosition) {
            (endPosition..startPosition)
                .forEach {
                    straightLines.add(Pair(second, it))
                }
        } else {
            (startPosition..endPosition)
                .forEach {
                    straightLines.add(Pair(second, it))
                }

        }
    }

    fun part1(input: List<String>): Int {
        val straightLines = mutableListOf<Pair<Int, Int>>()
        input
            .forEach {
                val coords = it.replace(" -> ", ",").split(",").map { it.trim().toInt() }
                val startPosition = Pair(coords[0], coords[1])
                val endPosition = Pair(coords[2], coords[3])
                if (startPosition.first == endPosition.first) {
                    addHorizontalLines(startPosition.second, endPosition.second, endPosition.first, straightLines)
                }
                if (startPosition.second == endPosition.second) {
                    addVerticalLines(startPosition.first, endPosition.first, endPosition.second, straightLines)
                }
            }
        return straightLines.groupBy { it }.filter { it.value.size > 1 }.count()
    }

    fun addDiagonalDoubleLinesLines(start: Pair<Int, Int>, end: Pair<Int, Int>, straightLines: MutableList<Pair<Int, Int>>) {
        var uplist = mutableListOf<Pair<Int, Int>>()
        var downList = mutableListOf<Pair<Int, Int>>()
        var leftList = mutableListOf<Pair<Int, Int>>()
        var rightList = mutableListOf<Pair<Int, Int>>()
        (0..999)
            .forEach {
                val upPair = Pair(start.first + it, start.second + it)
                val downPair = Pair(start.first - it, start.second - it)
                val leftpair = Pair(start.first + it, start.second - it)
                val rightpair = Pair(start.first - it, start.second + it)
                uplist.add(upPair)
                downList.add(downPair)
                leftList.add(leftpair)
                rightList.add(rightpair)
                if (upPair == end) {
                    straightLines.addAll(uplist)
                }
                if (downPair == end) {
                    straightLines.addAll(downList)
                }

                if (leftpair == end) {
                    straightLines.addAll(leftList)
                }
                if (rightpair == end) {
                    straightLines.addAll(rightList)
                }
            }
    }


    fun isADiagonal(startPosition: Pair<Int, Int>, endPosition: Pair<Int, Int>): Boolean {
        var listOfPossibleDiagonals = mutableListOf<Pair<Int, Int>>()

        (0..999)
            .forEach {
                listOfPossibleDiagonals.add(Pair(startPosition.first - it, startPosition.second - it))
                listOfPossibleDiagonals.add(Pair(startPosition.first - it, startPosition.second + it))
                listOfPossibleDiagonals.add(Pair(startPosition.first + it, startPosition.second - it))
                listOfPossibleDiagonals.add(Pair(startPosition.first + it, startPosition.second + it))
            }
        return listOfPossibleDiagonals.contains(endPosition)
    }

    fun part2(input: List<String>): Int {
        val straightLines = mutableListOf<Pair<Int, Int>>()
        input
            .forEach {
                val coords = it.replace(" -> ", ",").split(",").map { it.trim().toInt() }
                val startPosition = Pair(coords[0], coords[1])
                val endPosition = Pair(coords[2], coords[3])

                if (isADiagonal(startPosition, endPosition)) {
                    addDiagonalDoubleLinesLines(startPosition, endPosition, straightLines)
                }
                if (startPosition.first == endPosition.first) {
                    addHorizontalLines(startPosition.second, endPosition.second, endPosition.first, straightLines)
                }
                if (startPosition.second == endPosition.second) {
                    addVerticalLines(startPosition.first, endPosition.first, endPosition.second, straightLines)
                }
            }
        return straightLines.groupBy { it }.filter { it.value.size > 1 }.count()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05", "_test")
    println(part1(testInput))
    check(part1(testInput) == 5)
    println(part2(testInput))
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    check(part1(input) == 4826)
    check(part2(input) == 16793)
    println(part1(input))
    println(part2(input))
}

