fun main() {
    fun part1(input: List<String>): Int {

        var mostCalories = 0

        var sumCalories = 0
        for (item in input) {
            if (item.length == 0) {
                if (sumCalories > mostCalories) {
                    mostCalories = sumCalories
                }
                sumCalories = 0
                // println("Maximum calories: $mostCalories")
            } else {
                sumCalories = sumCalories + item.toInt()
                // println("Sum of calories: $sumCalories")
            }
        }
        return mostCalories
    }

    fun part2(input: List<String>): Int {
        val topThreeMostCalories: IntArray = intArrayOf(0, 0, 0)
        var sumCalories = 0

        for (item in input) if (item.length == 0) {
            if (sumCalories > topThreeMostCalories[0]) {
                topThreeMostCalories[0] = sumCalories
                topThreeMostCalories.sort()
                for(cal in topThreeMostCalories) {
                    print("$cal ")
                }
                println()
            }
            sumCalories = 0
        } else {
            sumCalories += item.toInt()
        }
        return topThreeMostCalories.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    println(part1(testInput))
    println(part2(testInput))
    // check(part1(testInput) == 1)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
