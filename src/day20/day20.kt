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
                print("${pixels[Point(row, column)] ?: 'O'}")
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
        val currentRow = minXValue(pixelGrid.keys)
        val lastRow = maxXValue(pixelGrid.keys)
        val currentColumn = minYValue(pixelGrid.keys)
        val lastColumn = maxYValue(pixelGrid.keys)

        (currentRow - 1 until currentRow).forEach { row ->
            (currentColumn - 1..lastColumn + 1).forEach { column ->
                paddedPixels[Point(row, column)] = '.'
            }
        }
        (lastRow + 1..lastRow + 1).forEach { row ->
            (currentColumn - 1..lastColumn + 1).forEach { column ->
                paddedPixels[Point(row, column)] = '.'
            }
        }
        (lastColumn + 1..lastColumn + 1).forEach { column ->
            (currentRow..lastRow).forEach { row ->
                paddedPixels[Point(row, column)] = '.'
            }
        }
        (currentColumn - 1..currentColumn - 1).forEach { column ->
            (currentRow..lastRow).forEach { row ->
                paddedPixels[Point(row, column)] = '.'
            }
        }
        return paddedPixels
    }

    fun anyLitUpPixelsOnEdge(pixelGrid: MutableMap<Point, Char>): Boolean {
        var tempGrid = mutableListOf<Char>()

        val firstRow = minXValue(pixelGrid.keys)
        val lastRow = maxXValue(pixelGrid.keys)
        val firstColumn = minYValue(pixelGrid.keys)
        val lastColumn = maxYValue(pixelGrid.keys)

        (firstRow..lastRow).forEach { row ->
            tempGrid.add(pixelGrid[Point(row, firstColumn)] ?: throw RuntimeException("SUS"))
            tempGrid.add(pixelGrid[Point(row, lastColumn)] ?: throw RuntimeException("SUS"))
        }
        (firstColumn..lastColumn).forEach { column ->
            tempGrid.add(pixelGrid[Point(firstRow, column)] ?: throw RuntimeException("SUS"))
            tempGrid.add(pixelGrid[Point(lastRow, column)] ?: throw RuntimeException("SUS"))
        }

        if(!tempGrid.contains('.')) throw RuntimeException("No ' ")
        return tempGrid.contains('#')
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

        repeat((0..1).count()) {
            var newPixelGrid = mutableMapOf<Point, Char>()
            if (anyLitUpPixelsOnEdge(pixelGrid)) pixelGrid.putAll(padImage(pixelGrid))
            pixelGrid.forEach { (key, value) ->
                newPixelGrid.putAll(findValuesFor(key, pixelGrid, binaryLookup))
            }
            pixelGrid.clear()
            pixelGrid.putAll(newPixelGrid)
            printGridWithChar(pixelGrid)
        }
        printGridWithChar(pixelGrid)
        return pixelGrid.values.count { it == '#' }
    }

    fun part2(input: List<String>): Int {
        val binaryLookup = input[0].toList()

        var pixelGrid = mutableMapOf<Point, Char>()
        val listOfPixels = input.filterIndexed { index, _ -> (index > 1) }.map { it.toCharArray().toList() }

        repeat((listOfPixels.indices).count()) { row ->
            repeat((listOfPixels[row].indices).count()) { column ->
                pixelGrid[Point(row + 1, column + 1)] = listOfPixels[row][column]
            }
        }

        repeat((1..50).count()) {
            var newPixelGrid = mutableMapOf<Point, Char>()
            if (anyLitUpPixelsOnEdge(pixelGrid)) pixelGrid.putAll(padImage(pixelGrid))
            pixelGrid.forEach { (key, value) ->
                newPixelGrid.putAll(findValuesFor(key, pixelGrid, binaryLookup))
            }
            pixelGrid.clear()
            pixelGrid.putAll(newPixelGrid)
        }
        printGridWithChar(pixelGrid)
        return pixelGrid.values.count { it == '#' }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day20", "_test")
//    println(part1(testInput))
//    check(part1(testInput) == 35)
//    println(part2(testInput))
//    check(part2(testInput) == 3351)

    val input = readInput("Day20")
//    println(part1(input))
//    check(part1(input) == 5483)
//    //Too High 20330
    println(part2(input))
    check(part2(input) == 5326)
}

