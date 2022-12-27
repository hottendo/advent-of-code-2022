import kotlin.math.absoluteValue
import kotlin.math.max

private data class Knot(var x: Int = 0, var y: Int = 0) {

    fun getXY(): Pair<Int, Int> {
        return Pair(x, y)
    }

    fun step(direction: String) {
        when (direction) {
            "R" -> x++
            "U" -> y--
            "D" -> y++
            "L" -> x--
            "UR" -> {
                y--
                x++
            }

            "RD" -> {
                x++
                y++
            }

            "DL" -> {
                y++
                x--
            }

            "LU" -> {
                x--
                y--
            }

            else -> {
                println("ERROR: operator $direction is invalid!")
            }
        }
    }
}

private class Rope(val noOfTailKnots: Int = 1) {
    var head = Knot()
    val tailKnots = Array<Knot>(noOfTailKnots){Knot()}
    val tailPositions = mutableSetOf(Pair<Int, Int>(tailKnots.last().x, tailKnots.last().y))

    fun moveNext(x: Int, y: Int, knot: Knot): Pair<Int, Int>  {
        val distance = max((x - knot.x).absoluteValue, (y - knot.y).absoluteValue)
        if (distance > 1) {
            val tailDirection = calcTailStep(x, y, knot.x, knot.y)
            knot.step(tailDirection)
        }
        return Pair(knot.x, knot.y)
    }

    fun move(direction: String, steps: Int) {
        println("Dir: $direction\tSteps: $steps")
        for (i in 0 until steps) {
            head.step(direction)
            var (x, y) = head.getXY()
            for (j in 0 until noOfTailKnots) {
                with(moveNext(x, y, tailKnots[j])) { x = first; y = second }
            }
            tailPositions.add(Pair(x, y))
            // println("Tail step: $tailDirection 1")
        }
    }

    fun calcTailStep(x1: Int, y1: Int, x2: Int, y2: Int): String {
        var direction = ""
        val dy = y1 - y2
        val dx = x1 - x2

        if (dy < 0) {
            direction = "U"
            if (dx < 0) {
                direction = "LU"
            }
            if (dx > 0) {
                direction = "UR"
            }
        }
        if (dy > 0) {
            direction = "D"
            if (dx > 0) {
                direction = "RD"
            }
            if (dx < 0) {
                direction = "DL"
            }
        }
        if (dy == 0) {
            if (dx < 0) {
                direction = "L"
            }
            if (dx > 0) {
                direction = "R"
            }
        }
        return direction
    }
}

fun main() {

    fun check(): Int {
        return 0
    }

    fun part1(input: List<String>): Int {
        val score: Int
        val rope = Rope(1)

        for (cmd in input) {
            val tokens = cmd.split(' ')
            val dir = tokens.first()
            val steps = tokens.last().toInt()
            rope.move(dir, steps)
            // calculate tail move
        }
        score = rope.tailPositions.size
        return score
    }

    fun part2(input: List<String>): Int {
        val score: Int
        val rope = Rope(9)

        for (cmd in input) {
            val tokens = cmd.split(' ')
            val dir = tokens.first()
            val steps = tokens.last().toInt()
            rope.move(dir, steps)
            // calculate tail move
        }
        score = rope.tailPositions.size
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 13)
    println("Part 1 (Test): ${part1(testInput)}")
    check(part2(testInput) == 1)
    println("Part 2.1 (Test): ${part2(testInput)}")

    val testInput2 = readInput("Day09_2_test")
    check(part2(testInput2) == 36)
    println("Part 2.1 (Test): ${part2(testInput2)}")

    val input = readInput("Day09")
    println("Part 1       : ${part1(input)}")
    println("Part 2       : ${part2(input)}")
}
