
fun main() {

    fun isMarker(marker: String, markerSize: Int): Boolean {
        /* takes a 4 character string as input and checks for duplicate chars.
        No duplicates -> marker found.
         */
        if(marker.toSet().size == markerSize) {
            return true
        }
        return false
    }

    fun part1(input: List<String>): Int {
        var score = 0
        val MARKER_SIZE = 4

        for(item in input) {
            score = 0
            while(score < item.length - MARKER_SIZE - 1) {
                val markerCandidate = item.slice(score..score + MARKER_SIZE - 1)
                if (isMarker(markerCandidate, MARKER_SIZE)) {
                    break
                }
                score += 1
            }
        }
        return score + MARKER_SIZE
    }

    fun part2(input: List<String>): Int {
        var score = 0
        val MARKER_SIZE = 14

        for(item in input) {
            score = 0
            while(score < item.length - MARKER_SIZE - 1) {
                val markerCandidate = item.slice(score..score + MARKER_SIZE - 1)
                if (isMarker(markerCandidate, MARKER_SIZE)) {
                    break
                }
                score += 1
            }
        }
        return score + MARKER_SIZE
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    println("Part 1 (Test): ${part1(testInput)}")
    println("Part 2 (Test): ${part2(testInput)}")
    // check(part1(testInput) == 1)

    val input = readInput("Day06")
    println("Part 1       : ${part1(input)}")
    println("Part 2       : ${part2(input)}")
}
