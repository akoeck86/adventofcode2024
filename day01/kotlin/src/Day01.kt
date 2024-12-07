import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.collections.mutableListOf
import kotlin.math.abs

fun main(args: Array<String>) {
    val lines = readInput(args[0])
    println("Distance: ${calculateDistance(lines)}")
    println("Similarity: ${calculateSimilarity(lines)}")
}

data class IntLists(val firstList: List<Int>, val secondList: List<Int>)

fun readInput(filename: String) = Path(filename).readLines()

fun splitLines(lines: List<String>) : IntLists {
    val firstList = mutableListOf<Int>()
    val secondList = mutableListOf<Int>()
    for(line in lines) {
        val parts = line.split("\\s+".toRegex())
        firstList.add(parts[0].toInt())
        secondList.add(parts[1].toInt())
    }
    return IntLists(firstList, secondList)
}

fun calculateDistance(lines: List<String>): Int {
    val intLists = splitLines(lines)
    val firstList = intLists.firstList.toMutableList().sorted()
    val secondList = intLists.secondList.toMutableList().sorted()
    val distances = mutableListOf<Int>()
    firstList.forEachIndexed { index, i ->  distances.add(abs(i - secondList[index])) }
    return distances.sum()
}

fun calculateSimilarity(lines: List<String>) : Int {
    val intLists = splitLines(lines)
    val similarities = mutableListOf<Int>()
    intLists.firstList.forEach{ item -> similarities.add(item * intLists.secondList.count {  it == item })}
    return similarities.sum()
}