private class Tube(val cycles: IntArray) {
    var cycleCount = 0
    var register = 1
    var signalStrength = 0
    val screen = CharArray(240) { '.' }

    fun calculateSignalStrength(): Int {
        var signalStrength = 0

        if (cycleCount in cycles) {
            signalStrength = register * cycleCount
        }
        return signalStrength
    }

    fun isPixelLit(): Char {
        val xPosition = cycleCount.mod(40)
        if (xPosition >= (register - 1) && xPosition <= (register + 1)) {
            return '#'
        }
        return '.'
    }

    fun runCrt(input: List<String>) {
        for (item in input) {
            val cmd = item.split(' ').first()
            val arg: Int
            if (cmd == "addx") {
                arg = item.split(' ').last().toInt()
                screen[cycleCount] = isPixelLit()
                cycleCount++
                screen[cycleCount] = isPixelLit()
                register += arg
            } else if (cmd == "noop") {
                screen[cycleCount] = isPixelLit()
            }
            cycleCount++
        }
    }

    fun run(input: List<String>) {
        for (item in input) {
            val cmd = item.split(' ').first()
            val arg: Int
            if (cmd == "addx") {
                arg = item.split(' ').last().toInt()
                cycleCount++
                signalStrength += calculateSignalStrength()
                cycleCount++
                signalStrength += calculateSignalStrength()
                register += arg
            } else if (cmd == "noop") {
                cycleCount++
                signalStrength += calculateSignalStrength()
            }
        }
    }
}

fun main() {

    fun check(): Int {
        return 0
    }

    fun part1(input: List<String>): Int {
        val score: Int
        val tube = Tube(intArrayOf(20, 60, 100, 140, 180, 220))

        tube.run(input)
        score = tube.signalStrength
        return score
    }

    fun part2(input: List<String>): Int {
        val score = 0
        val tube = Tube(intArrayOf(20, 60, 100, 140, 180, 220))

        tube.runCrt(input)

        var i = 0
        for (y in 0 until 6) {
            for (x in 0 until 40) {
                print(tube.screen[i++])
            }
            println()
        }

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 13140)
    // println("Part 1 (Test): ${part1(testInput)}")
    // check(part2(testInput) == 1)
    // part2(testInput)
    // println("Part 2 (Test): ${part2(testInput)}")

    // val testInput2 = readInput("Day09_2_test")
    // check(part2(testInput2) == 36)
    //println("Part 2.1 (Test): ${part2(testInput2)}")

    val input = readInput("Day10")
    // println("Part 1       : ${part1(input)}")
    println("Part 2       : ${part2(input)}")
}
