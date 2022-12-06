
class Stack(crates: List<String>) {
    var size = crates.size
    var stackOfCrates = crates.toMutableList()

    fun moveTo(otherStack: Stack, count: Int) {

        // add count creates to the other stack (part 1)
        // val movingCrates: List<String> = this.stackOfCrates.take(count).reversed()

        // part 2: crate order does not get reversed
        val movingCrates: List<String> = this.stackOfCrates.take(count)
        otherStack.stackCrates(movingCrates)

        // remove crates from this stack
        removeCrates(count)
        size = stackOfCrates.size
    }

    fun stackCrates(cratesToAdd: List<String>): Int {
        this.stackOfCrates = (cratesToAdd.toMutableList() + this.stackOfCrates) as MutableList<String>
        this.size = this.stackOfCrates.size
        return size
    }

    fun removeCrates(count: Int) {
        for(i in 0..(count-1)) {
            this.stackOfCrates.removeAt(0)
        }
    }

    override fun toString(): String {
        var str = "Stack (size: $size) - ${stackOfCrates}\n"
        return str
    }

    fun first(): String {
        return stackOfCrates[0]
    }
}

fun main() {


    fun part1(input: List<String>): String {
        var score = ""

        val stacks = mutableListOf<Stack>()
        stacks.add(Stack(mutableListOf("T", "Z", "B")))
        stacks.add(Stack(mutableListOf("N", "D", "T", "H", "V")))
        stacks.add(Stack(mutableListOf("D", "M", "F", "B")))
        stacks.add(Stack(mutableListOf("L", "Q", "V", "W", "G", "J", "T")))
        stacks.add(Stack(mutableListOf("M", "Q", "F", "V", "P", "G", "D", "W")))
        stacks.add(Stack(mutableListOf("S", "F", "H", "G", "Q", "Z", "V")))
        stacks.add(Stack(mutableListOf("W", "C", "T", "L", "R", "N", "S", "Z")))
        stacks.add(Stack(mutableListOf("M", "R", "N", "J", "D", "W", "H", "Z")))
        stacks.add(Stack(mutableListOf("S", "D", "F", "L", "Q", "M")))
        for(s in stacks) {
            print(s)
        }

        val regex = "^move (\\d+) from (\\d+) to (\\d+)$".toRegex()

        for(instr in input) {
            val match = regex.find(instr)

            println(instr)
            if(match != null) {
                val count = match.groupValues[1].toInt()
                val fromStack = stacks[match.groupValues[2].toInt()-1]
                val toStack = stacks[match.groupValues[3].toInt()-1]
                fromStack.moveTo(toStack, count)
                print(fromStack)
                print(toStack)
                println()
            }
        }
        for(s in stacks) {
            score += s.first()
            print(s)
        }

        return score
    }

    fun part2(input: List<String>): Int {
        var score = 0

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    // println("Part 1 (Test): ${part1(testInput)}")
    // println("Part 2 (Test): ${part2(testInput)}")
    // check(part1(testInput) == 1)

    val input = readInput("Day05")
    println("Part 1       : ${part1(input)}")
    // println("Part 2       : ${part2(input)}")
}
