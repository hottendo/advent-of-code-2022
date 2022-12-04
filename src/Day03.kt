
fun main() {

    fun getItemPriority(character: Char): Int {
        val lowerCaseAsciiOffset = 96 // ascii code of a is 97
        val upperCaseAsciiOffset = 64 // ascii code of A is 65
        val priority: Int

        // println("Char: $character - Code: ${character.code}")
        if (character.code < 97) { // upper case
            priority = character.code - upperCaseAsciiOffset + 26
        } else {
            priority = character.code - lowerCaseAsciiOffset
        }
        // println("$priority")
        return priority
    }

    fun part1(input: List<String>): Int {
        var score = 0

        val rucksackLeftCompartment = mutableSetOf<Char>()
        val rucksackRightCompartment = mutableSetOf<Char>()

        for(item in input) {
            //println(item)
            for(i in  0..item.length/2-1) {
                rucksackLeftCompartment.add(item[i])
            }
            for(i in item.length/2..item.length-1) {
                rucksackRightCompartment.add(item[i])
            }
            val doubleItems = rucksackLeftCompartment.intersect(rucksackRightCompartment)
            // println(doubleItems)

            if(doubleItems.size == 1) {
                score += getItemPriority(doubleItems.first())
            } else {
                println("Error: there are ${doubleItems.size} duplicate items!")
            }
            rucksackLeftCompartment.clear()
            rucksackRightCompartment.clear()
            doubleItems.drop(doubleItems.size)
        }
        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0
        val rucksack1 = mutableSetOf<Char>()
        val rucksack2 = mutableSetOf<Char>()
        val rucksack3 = mutableSetOf<Char>()
        val elvesGroups = input.chunked(3)
        for(group in elvesGroups) {
            for(c in group[0]) { rucksack1.add(c) }
            for(c in group[1]) { rucksack2.add(c) }
            for(c in group[2]) { rucksack3.add(c) }
            val badge = rucksack1 intersect rucksack2 intersect rucksack3
            score += getItemPriority(badge.first())
            rucksack1.clear()
            rucksack2.clear()
            rucksack3.clear()
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println(part1(testInput))
    println(part2(testInput))
    // check(part1(testInput) == 1)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
