/* keep and access a 3-dimensional grid data structure
* with the same number of columns and rows */
private class Grid() {
    var rows: Int = 0
    var cols: Int = 0
    var grid = Array(cols) { ByteArray(rows) { 0 } }

    constructor(input: List<String>) : this() {
        grid = buildGrid(input)
        rows = grid.size
        cols = grid.size
    }

    constructor(inGrid: Array<ByteArray>) : this() {
        grid = inGrid
        rows = grid.size
        cols = grid.size

    }

    // build the grid data structure (an array of arrays)
    fun buildGrid(input: List<String>): Array<ByteArray> {
        rows = input.size
        cols = input.size
        val grid = Array(rows) { ByteArray(cols) }

        var j = 0
        for (line in input) {
            for (i in 0 until line.trim().length) {
                grid[j][i] = (line[i].code - '0'.code).toByte()
            }
            j++
        }
        return grid
    }

    fun transpose(): Array<ByteArray> {
        val transposedGrid = Array(cols) { ByteArray(rows) }
        for (i in 0 until rows) {
            for (j in 0 until cols) {
                transposedGrid[j][i] = grid[i][j]
            }
        }
        return transposedGrid
    }

    fun createVisibilityGrid(): Array<ByteArray> {
        val visible = Array(cols) { ByteArray(rows) { 0 } }

        // set the first and last row to 1
        for (j in intArrayOf(0, rows - 1)) {
            for (i in 0 until cols - 1) {
                visible[j][i] = 1
            }
        }
        // set the first and last column to 1
        for (j in 1 until rows - 1) {
            for (i in intArrayOf(0, cols - 1)) {
                visible[j][i] = 1
            }
        }
        return visible
    }

    fun checkVisibility(): Int {
        var maxHeight: Byte
        val visible = createVisibilityGrid()

        // check every row for visible trees
        for (j in 0 until rows) {
            // left to right
            maxHeight = grid[j][0]
            for (i in 1 until cols) {
                if (maxHeight < grid[j][i]) {
                    visible[j][i] = 1
                    maxHeight = grid[j][i]
                }
            }
            // right to left
            maxHeight = grid[j][cols - 1]
            for (i in cols - 1 downTo 0) {
                if (maxHeight < grid[j][i]) {
                    visible[j][i] = 1
                    maxHeight = grid[j][i]
                }
            }
        }

        // check every column for visible trees
        for (i in 0 until cols) {
            // top to bottom
            maxHeight = grid[0][i]
            for (j in 1 until rows) {
                if (maxHeight < grid[j][i]) {
                    visible[j][i] = 1
                    maxHeight = grid[j][i]
                }
            }
            // bottom to top
            maxHeight = grid[rows - 1][i]
            for (j in cols - 1 downTo 0) {
                if (maxHeight < grid[j][i]) {
                    visible[j][i] = 1
                    maxHeight = grid[j][i]
                }
            }
        }
        var visibleCount = 0
        for (j in 0 until rows) {
            for (i in 0 until cols) {
                visibleCount += visible[j][i]
            }
        }
        println(Grid(visible))
        return visibleCount
    }

    fun getHeight(r: Int, c: Int): Byte {
        var height = 127.toByte()
        if (r < rows && c < cols) {
            height = grid[r][c]
        } else {
            println("ERROR: Indices ($r, $c) out of bounds.")
        }
        return height
    }

    fun calcScenicScore(r: Int, c: Int): Int {
        var score = 1
        var i: Int
        val height = getHeight(r, c)

        // for the row to the left
        var viewCount = 0
        if (c > 0) {
            i = c
            do {
                i--
                viewCount++
            } while (grid[r][i] < height && i > 0)
            score *= viewCount
        }

        // for the row to the right
        viewCount = 0
        if (c < cols - 1) {
            i = c
            do {
                i++
                viewCount++
            } while (grid[r][i] < height && i < (cols - 1))
            score *= viewCount
        }

        // for the column to the top
        viewCount = 0
        if (r > 0) {
            i = r
            do {
                i--
                viewCount++
            } while (grid[i][c] < height && i > 0)
            score *= viewCount
        }

        // for the column to the bottom
        viewCount = 0
        if (r < rows - 1) {
            i = r
            do {
                i++
                viewCount++
            } while (grid[i][c] < height && i < (rows - 1))
            score *= viewCount
        }
        return score
    }

    fun getMaxScenicScore(): Int {
        var maxScore = 0
        var score: Int
        for (j in 1 until rows - 1) {
            for (i in 1 until cols - 1) {
                score = calcScenicScore(j, i)
                if (maxScore < score) {
                    maxScore = score
                }
            }
        }
        return maxScore
    }

    override fun toString(): String {
        var str = "|"
        for (j in 0 until rows) {
            for (i in 0 until cols) {
                str += grid[j][i].toString() + "|"
            }
            str += "\n|"
        }
        return str
    }
}

fun main() {

    fun check(): Int {
        return 0
    }

    fun part1(input: List<String>): Int {
        var score: Int
        val forest = Grid(input)

        score = forest.checkVisibility()

        return score
    }

    fun part2(input: List<String>): Int {
        var score: Int
        val forest = Grid(input)
        score = forest.getMaxScenicScore()
        // score = forest.calcScenicScore(1, 2)
        // score = forest.calcScenicScore(3, 2)

        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    // println("Part 1 (Test): ${part1(testInput)}")
    println("Part 2 (Test): ${part2(testInput)}")
    // check(part1(testInput) == 1)

    val input = readInput("Day08")
    // println("Part 1       : ${part1(input)}")
    println("Part 2       : ${part2(input)}")
}
