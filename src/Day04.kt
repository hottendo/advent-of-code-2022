
fun main() {


    fun doCompleteSectionsOverlap(a: String, b: String): Boolean {
        val firstElvesSections = a.split('-').map { it.toInt() }
        val secondElvesSections = b.split('-').map { it.toInt() }

        // Test: first elve's sections completely in second elves sections
        if(firstElvesSections.first() >= secondElvesSections.first() &&
            firstElvesSections.last() <= secondElvesSections.last()) {
            return true
        }
        // Test: second elve's sections completely in first elve's sections
        if(secondElvesSections.first() >= firstElvesSections.first() &&
            secondElvesSections.last() <= firstElvesSections.last()) {
            return true
        }
        return false
    }

    fun doPartialSectionsOverlap(a: String, b: String): Boolean {
        val firstElvesSections = a.split('-').map { it.toInt() }
        val secondElvesSections = b.split('-').map { it.toInt() }

        // Test: first elve's sections completely in second elves sections
        if(firstElvesSections.last() >= secondElvesSections.first() &&
            firstElvesSections.first() <= secondElvesSections.last()) {
            return true
        }
        return false
    }

    fun part1(input: List<String>): Int {
        var score = 0

        for(item in input) {
            val sections = item.split(',')
            if (doCompleteSectionsOverlap(sections.first(), sections.last())) {
                score += 1
            }
        }
        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0

        for(item in input) {
            val sections = item.split(',')
            if (doPartialSectionsOverlap(sections.first(), sections.last())) {
                score += 1
            }
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    println("Part 1 (Test): ${part1(testInput)}")
    println("Part 2 (Test): ${part2(testInput)}")
    // check(part1(testInput) == 1)

    val input = readInput("Day04")
    println("Part 1       : ${part1(input)}")
    println("Part 2       : ${part2(input)}")
}
