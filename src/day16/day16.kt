package day16

import java.math.BigInteger
import readInput


fun main() {

    fun String.getPacketVersion(): Pair<Int, CharSequence> {
        val packetVersion = this.take(3).toInt(2).also { println("Packet Version: $it") }
        return Pair(packetVersion, this.subSequence(3, this.length))
    }

    fun CharSequence.getTypeID(): Pair<Int, CharSequence> {
        val typeID = this.subSequence(0, 3).toString().toInt(2).also { println("Type ID: $it") }
        return Pair(typeID, this.subSequence(3, this.length))
    }

    fun CharSequence.getLengthType(): Pair<Int, CharSequence> {
        val lengthType = this.subSequence(0, 1).toString().toInt(2).also { println("Length Type: $it") }
        return Pair(lengthType, this.subSequence(1, this.length))
    }

    fun CharSequence.getTotalLengths(): Pair<Int, CharSequence> {
        val totalLengths = this.subSequence(0, 15).toString().toInt(2).also { println("Total Length: $it") }
        return Pair(totalLengths, this.subSequence(15, this.length))
    }

    fun CharSequence.getNumberOfPackets(): Pair<Int, CharSequence> {
        val numberOfPackets = this.subSequence(0, 11).toString().toInt(2).also { println("Number of Packets: $it") }
        return Pair(numberOfPackets, this.subSequence(11, this.length))
    }

    fun CharSequence.removeLiteralBits(): CharSequence {
        var fiveBits = this.subSequence(0, 5).also { println("Initial 5 bits: $it") }
        var remainingBits = this.also { println("Remaining bits: $it") }
        while (fiveBits.first() != '0') {
            remainingBits = remainingBits.subSequence(5, remainingBits.length).also { println("Remove first 5 bits: $it") }
            fiveBits = remainingBits.subSequence(0, 5)
        }
        return remainingBits.subSequence(5, remainingBits.length)
    }

    fun part1(input: List<String>): Int {
        var binaryString = input[0].toCharArray().joinToString { it.toHexString() }.replace(", ", "").also { println(it) }
        var versionNoCount = 0

        val (versionNo, bitsWithoutVersionNo) = binaryString.getPacketVersion()
        val (typeId, bitsWithoutTypeID) = bitsWithoutVersionNo.getTypeID()
        
        if (typeId != 4) {
            //Operator
            val (lengthType, bitsWithoutLengthType) = bitsWithoutTypeID.getLengthType()
            when (lengthType) {
                1 -> {
                    val (totalLength, bitsWithoutTotalLength) = bitsWithoutLengthType.getTotalLengths()
                }
                0 -> {
                    val (noOfPackets, bitsWithoutNoOfPackets) = bitsWithoutLengthType.getNumberOfPackets()
                }
            }
        } else {
            val remainingBits = bitsWithoutTypeID.removeLiteralBits()
            println("RemainingBits: $remainingBits")
        }

        println(versionNoCount)
        return 0

    }

    fun part2(input: List<String>): Int {
        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16", "_test")
    println(part1(testInput))
//    check(part1(testInput) == 0)
//    println(part2(testInput))
//    check(part2(testInput) == 0)

    val input = readInput("Day16")
//    println(part1(input))
//    println(part2(input))
//    check(part1(input) == 0)
//    check(part2(input) == 0)
}

fun Char.toHexString(): String {
    return BigInteger(this.toString(), 16).toString(2).padStart(4, '0').also { println("$this -> $it") }
}
