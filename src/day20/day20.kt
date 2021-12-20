package day20

import java.awt.Point
import java.lang.Integer.parseInt
import readInput

fun main() {

    fun minXValue(pixelsToDecode: MutableSet<Point>) = pixelsToDecode.map { it.x }.toList().minOrNull() ?: 0

    fun maxXValue(pixelsToDecode: MutableSet<Point>) = pixelsToDecode.map { it.x }.toList().maxOrNull() ?: 0

    fun minYValue(pixelsToDecode: MutableSet<Point>) = pixelsToDecode.map { it.y }.toList().minOrNull() ?: 0

    fun maxYValue(pixelsToDecode: MutableSet<Point>) = pixelsToDecode.map { it.y }.toList().maxOrNull() ?: 0

    fun printGridWithChar(pixels: MutableMap<Point, Char>) {
        (minXValue(pixels.keys)..maxXValue(pixels.keys)).forEach { row ->
            println()
            (minYValue(pixels.keys)..maxYValue(pixels.keys)).forEach { column ->
                print("${pixels[Point(row, column)]}")
            }
        }
    }

    fun getSurroundingPixels(it: Point): MutableMap<Point, String> {
        val ninePixels = mutableMapOf<Point, String>()
        Point(it.x - 1, it.y).also { ninePixels[it] = "(${it.x},${it.y})" } //above
        Point(it.x + 1, it.y).also { ninePixels[it] = "(${it.x},${it.y})" } //below
        Point(it.x, it.y - 1).also { ninePixels[it] = "(${it.x},${it.y})" } //left
        Point(it.x, it.y + 1).also { ninePixels[it] = "(${it.x},${it.y})" } //right
        Point(it.x, it.y).also { ninePixels[it] = "(${it.x},${it.y})" } //right
        Point(it.x - 1, it.y + 1).also { ninePixels[it] = "(${it.x},${it.y})" } //topRight
        Point(it.x - 1, it.y - 1).also { ninePixels[it] = "(${it.x},${it.y})" } //topLeft
        Point(it.x + 1, it.y + 1).also { ninePixels[it] = "(${it.x},${it.y})" } //bottomRight
        Point(it.x + 1, it.y - 1).also { ninePixels[it] = "(${it.x},${it.y})" } //bottomLeft
        return ninePixels
    }

    fun findValuesFor(pointOnGrid: Point, pixelGrid: MutableMap<Point, Char>, binaryLookup: List<Char>): MutableMap<Point, Char> {
        val pixelsToDecode = getSurroundingPixels(pointOnGrid)

        var listOfPixelValues = mutableListOf<Char>()
        var listOfNewPoints = mutableMapOf<Point, Char>()

        (minXValue(pixelsToDecode.keys)..maxXValue(pixelsToDecode.keys)).forEach { row ->
            (minYValue(pixelsToDecode.keys)..maxYValue(pixelsToDecode.keys)).forEach { column ->
                listOfPixelValues.add(pixelGrid[Point(row, column)] ?: '.'.also { listOfNewPoints[Point(row, column)] = '.' })
            }
        }

        val binaryNumber = parseInt(listOfPixelValues.map { if (it == '.') 0 else 1 }.joinToString().replace(", ", ""), 2)

        listOfNewPoints[pointOnGrid] = binaryLookup[binaryNumber]
        return listOfNewPoints
    }

    fun padImage(pixelGrid: MutableMap<Point, Char>): MutableMap<Point, Char> {
        var paddedPixels = mutableMapOf<Point, Char>()
        val previousRow = minXValue(pixelGrid.keys) - 1
        val rowAfterLast = maxXValue(pixelGrid.keys) + 1
        val previousColumn = minYValue(pixelGrid.keys) - 1
        val columnAfterLast = maxYValue(pixelGrid.keys) + 1

        (previousRow..rowAfterLast).forEach { row ->
            paddedPixels[Point(row, previousColumn)] = '.'
            paddedPixels[Point(row, columnAfterLast)] = '.'
        }
        (previousColumn..columnAfterLast).forEach { column ->
            paddedPixels[Point(previousColumn, column)] = '.'
            paddedPixels[Point(columnAfterLast, column)] = '.'
        }

        return paddedPixels
    }

    fun part1(input: List<String>): Int {
        val binaryLookup = input[0].toList()

        var pixelGrid = mutableMapOf<Point, Char>()
        val listOfPixels = input.filterIndexed { index, _ -> (index > 1) }.map { it.toCharArray().toList() }

        repeat((listOfPixels.indices).count()) { row ->
            repeat((listOfPixels[row].indices).count()) { column ->
                pixelGrid[Point(row + 1, column + 1)] = listOfPixels[row][column]
            }
        }

        var firstPassPixelGrid = mutableMapOf<Point, Char>()
        var secondPassPixelGrid = mutableMapOf<Point, Char>()

        pixelGrid.putAll(padImage(pixelGrid))

        pixelGrid.forEach { (key, value) ->
            firstPassPixelGrid.putAll(findValuesFor(key, pixelGrid, binaryLookup))
        }

        firstPassPixelGrid.forEach { (key, value) ->
            secondPassPixelGrid.putAll(findValuesFor(key, firstPassPixelGrid, binaryLookup))
        }

        printGridWithChar(secondPassPixelGrid)

        return secondPassPixelGrid.values.count { it == '#' }
    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20", "_test")
//    println(part1(testInput))
    check(part1(testInput) == 35)
//    println(part2(testInput))
//    check(part2(testInput) == 0)

    val input = readInput("Day20")
    println(part1(input))
//    println(part2(input))
    check(part1(input) == 5483)
//    check(part2(input) == 0)
}

