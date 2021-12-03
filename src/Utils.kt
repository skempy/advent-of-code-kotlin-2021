import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, test: String = "") = File("src/${name.lowercase()}", "$name$test.txt").readLines()

fun readInputAsInt(name: String, test: String = "") = File("src/${name.lowercase()}", "$name$test.txt").readLines().map { it.toInt() }

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)
