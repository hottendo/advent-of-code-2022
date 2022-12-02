
enum class Score (val score: Int) {
    WIN(6),
    LOSS(0),
    DRAW(3)
}

fun main() {

    fun part1(input: List<String>): Int {
        val winningMoves = mapOf<String, Int>("C X" to 1, "B Z" to 3, "A Y" to 2)
        val loosingMoves = mapOf<String, Int>("B X" to 1, "A Z" to 3, "C Y" to 2)
        val drawMoves = mapOf<String, Int>("A X" to 1, "C Z" to 3, "B Y" to 2)

        var turn: String
        var score = 0
        for (item in input) {
            turn = item.trim()

            // determine outcome and assign score
            when(turn) {
                in winningMoves -> score += Score.WIN.score + winningMoves.get(turn)!!
                in loosingMoves -> score += Score.LOSS.score + loosingMoves.get(turn)!!
                in drawMoves -> score += Score.DRAW.score + drawMoves.get(turn)!!
                else -> println("ERROR: Score for match $turn could not be determined.")
            }
        }
        return score
    }

    fun part2(input: List<String>): Int {

        fun getExpectedMove(turn: String): String {
            /*
            takes in an input pair of moves and determines the resulting turn
            according to the rules for part2 of this puzzle.
            */
            val winningMove = mapOf("C" to "X", "B" to "Z", "A" to "Y")
            val loosingMove = mapOf("B" to "X", "A" to "Z", "C" to "Y")
            val drawMove = mapOf("C" to "Z", "B" to "Y", "A" to "X")

            var newTurn = ""
            val elvesMove: String = turn.split(' ').first()
            val expectedTurn: String = turn.split(' ').last()

            when (expectedTurn) {
                "Y" -> newTurn = elvesMove + " " + drawMove[elvesMove]
                "X" -> newTurn = elvesMove + " " + loosingMove[elvesMove]
                "Z" -> newTurn = elvesMove + " " + winningMove[elvesMove]
            }
            return newTurn
        }

        val winningMoves = mapOf<String, Int>("C X" to 1, "B Z" to 3, "A Y" to 2)
        val loosingMoves = mapOf<String, Int>("B X" to 1, "A Z" to 3, "C Y" to 2)
        val drawMoves = mapOf<String, Int>("A X" to 1, "C Z" to 3, "B Y" to 2)

        var turn: String
        var score = 0
        var newTurn: String

        for (item in input) {
            turn = item.trim()

            // turn the expected move into a proper move
            newTurn = getExpectedMove(turn)

            // determine outcome and assign score
            when (newTurn) {
                in winningMoves -> score += Score.WIN.score + winningMoves.get(newTurn)!!
                in loosingMoves -> score += Score.LOSS.score + loosingMoves.get(newTurn)!!
                in drawMoves -> score += Score.DRAW.score + drawMoves.get(newTurn)!!
                else -> println("ERROR: Score for match $newTurn could not be determined.")
            }
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    println(part1(testInput))
    println(part2(testInput))
    // check(part1(testInput) == 1)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
