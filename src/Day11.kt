
private class Monkey() {
    var id = -1
    var items = mutableListOf<Long>()
    var operation: Operation? = null
    var divisor: Long = 0
    var otherMonkeysIds: Pair<Int, Int>? = null
    var otherMonkeys: Pair<Monkey, Monkey>? = null
    var commonDivider = 0L
    private var noOfInspectedItems = 0L


    fun getInspectedItemCount(): Long {
        return noOfInspectedItems
    }

    fun clone(): Any {
        val monkey = Monkey()
        monkey.id = id
        monkey.items.addAll(items)
        monkey.operation = operation
        monkey.divisor = divisor
        monkey.otherMonkeysIds = otherMonkeysIds
        monkey.otherMonkeys = otherMonkeys
        return monkey
    }

    fun setOperation(inp: String) {
        val expr = inp.split('=').last().trim()
        val (op1, operator, op2) = expr.split(' ')
        operation = Operation(op1, op2, operator.toCharArray().first())
    }

    fun inspectItems(relief: Long) {
        val removedItems = mutableListOf<Int>()

        noOfInspectedItems += items.size
        // if (items.size > 0) noOfInspectedItems++
        items.forEachIndexed {index, item ->
            // update worry level (apply operation)
            var newItem = operation!!.eval(item)

            // account for monkey getting bored (int division here)
            newItem = newItem / relief

            // test for recipient and throw the item
            if (newItem.mod(divisor) == 0L) {
                otherMonkeys!!.first.items.add(newItem)
            } else
                otherMonkeys!!.second.items.add(newItem)
            removedItems.add(index)
        }
        // remove the items from the objects list
        // sort the list to remove highest indices first to avoid index out of bounds.
        removedItems.sortDescending()
        removedItems.forEach {i -> items.removeAt(i)}
    }

    fun inspectItems2() {
        val removedItems = mutableListOf<Int>()

        noOfInspectedItems += items.size
        // if (items.size > 0) noOfInspectedItems++
        items.forEachIndexed {index, item ->
            // update worry level (apply operation)
            var newItem = operation!!.eval(item)

            // restrict the size of the newItem as the mod of the product of the divisors
            // of all monkeys thus avoiding number overflow.
            newItem = newItem.mod(commonDivider)

            // test for recipient and throw the item
            if (newItem.mod(divisor) == 0L) {
                otherMonkeys!!.first.items.add(newItem)
            } else
                otherMonkeys!!.second.items.add(newItem)
            removedItems.add(index)
        }
        // remove the items from the objects list
        // sort the list to remove highest indices first to avoid index out of bounds.
        removedItems.sortDescending()
        removedItems.forEach {i -> items.removeAt(i)}
    }

}

private class Operation(val operand1: String, val operand2: String, val operation: Char) {
    fun eval(worryLevel: Long): Long {
        var op1: Long
        var op2: Long
        var result: Long

        if (operand1 == "old") {
            op1 = worryLevel
        } else {
            op1 = operand1.toLong()
        }
        if (operand2 == "old") {
            op2 = worryLevel
        } else {
            op2 = operand2.toLong()
        }

        result = when (operation) {
            '+' -> op1 + op2
            '-' -> op1 - op2
            '*' -> op1 * op2
            '/' -> op1 / op2
            else -> 0L
        }
        if (result > Long.MAX_VALUE) {
            println("ERROR: item exceeds ${Long.MAX_VALUE} -> $result")
            return -1L
        }
        return result
    }
}

fun main() {

    fun check(): Int {
        return 0
    }

    fun parseMonkeys(input: List<String>): List<Monkey> {
        val monkeyList = mutableListOf<Monkey>()

        var monkey: Monkey? = null
        var firstMonkey = 0
        var secondMonkey: Int
        for(item in input) {
            // var worryLevels: List<Int>
            if (item.startsWith("Monkey ")) {
                monkeyList.add(Monkey())
                monkey = monkeyList.last()
                monkey.id = item.split(" ").last().dropLast(1).toInt()
            }
            else if (item.startsWith("  Starting items:")) {
                val tmp = item.split(": ").last()
                tmp.split(", ").forEach {
                    monkey!!.items.add(it.toLong())
                }
            }
            else if (item.startsWith("  Operation:")) {
                monkey!!.setOperation(item.split(": ").last())
            }
            else if (item.startsWith("  Test:")) {
                monkey!!.divisor = item.split(" ").last().toLong()
            }
            else if (item.startsWith("    If true")) {
                firstMonkey = item.split(" ").last().toInt()
            }
            else if (item.startsWith("    If false")) {
                secondMonkey = item.split(" ").last().toInt()
                monkey!!.otherMonkeysIds = Pair(firstMonkey, secondMonkey)
            }
        }
        var divider = 1L
        for (m in monkeyList) {
            m.otherMonkeys = Pair(monkeyList[m.otherMonkeysIds!!.first], monkeyList[m.otherMonkeysIds!!.second])
            divider *= m.divisor
        }
        for (m in monkeyList) m.commonDivider = divider
        return monkeyList
    }

    fun part1(input: List<String>): Long {
        var score: Long

        val monkeys = parseMonkeys((input))

        val activity = mutableListOf<Long>()
        for (round in 0 until 20) {
            for (thisMonkey in monkeys) {
                thisMonkey.inspectItems(3) // part 1: relief = 3
            }
        }
        monkeys.forEach { activity.add(it.getInspectedItemCount()) }
        println(activity)
        activity.sortDescending()
        score = activity[0] * activity[1]

        return score
    }

    fun part2(input: List<String>): Long {
        var score: Long

        val monkeys = parseMonkeys((input))

        val activity = mutableListOf<Long>()
        for (round in 0 until 10_000) {
            for (thisMonkey in monkeys) {
                thisMonkey.inspectItems2()
            }
        }
        monkeys.forEach { activity.add(it.getInspectedItemCount()) }
        println(activity)
        activity.sortDescending()
        score = activity[0] * activity[1]

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 10605L)
    println("Part 1 (Test): ${part1(testInput)}")
    check(part2(testInput) == 2_713_310_158L)
    //println("Part 2 (Test): ${part2(testInput)}")

    val input = readInput("Day11")
    println("Part 1       : ${part1(input)}")
    println("Part 2       : ${part2(input)}")
}
