
private class Dir(val name: String) {
    var parent: Dir? = null
    val files = mutableListOf <File> ()
    val children = mutableListOf<Dir>()
    var dirSize = 0L
    var path: String = ""

    fun addFile(file: File): Boolean {
        dirSize += file.size
        return files.add(file)
    }

    fun addChild(child: Dir): Dir {
        child.parent = this
        child.path = this.path + "/" + child.name
        children.add(child)
        return child
    }

    fun getTreeSize(): Long {
        var treeSize = 0L
        val childIterator = children.listIterator()

        while(childIterator.hasNext()) {
            treeSize += childIterator.next().getTreeSize()
        }
        return treeSize + dirSize
    }

    fun printTree() {
        val childIterator = children.listIterator()
        while(childIterator.hasNext()) {
            childIterator.next().printTree()
        }
        print(this)
    }

    /* Build and return a map to quickly access dir objects by path. Path rather than names to cope
    duplicate directory names.
     */
    fun buildMap(): MutableMap<String, Dir> {
        val dirMap = mutableMapOf(path to this)
        val childIterator = children.listIterator()
        while(childIterator.hasNext()) {
            dirMap.plusAssign(childIterator.next().buildMap())
        }
        return dirMap
    }

    override fun toString(): String {
        var str = "DIR: ${name} ($dirSize)\n"
        str +=    "    Parent: ${if(parent != null) parent!!.name else "None"}\n"
        for(f in files) {
            str += "\t" + f
        }
        return str
    }
}

private class File(val name: String, val size: Int) {
    override fun toString(): String {
        val str = "File: ${name}\tSize: ${size}\n"
        return str
    }
}

fun main() {

    fun test(): Int {
        val rootDir = Dir("/")
        rootDir.addFile(File("abcd.txt", 123456))
        rootDir.addFile(File("efgh.txt", 789000))

        val c1 = rootDir.addChild(Dir("usr"))
        c1.addFile(File("lhjkjhlk.csv", 9078986))

        val c2 = rootDir.addChild(Dir("var"))
        c2.addFile(File("lhjkjhjlöjlk.csv", 91078986))

        rootDir.printTree()
        val totalSize = rootDir.getTreeSize()
        println("Size of the tree: $totalSize")

        return 9
    }

    fun changeDirectory(dirName: String, dirTree: Dir, currentDir: Dir?): Dir? {
        var ret: Dir? = null

        if (dirName.equals("/")) {
            ret = dirTree
        } else if (dirName.equals("..")) {
            ret = currentDir!!.parent
        } else {
            for (child in currentDir!!.children) {
                if (dirName.equals(child.name)) {
                    ret = child
                }
            }
        }
        return ret
    }

    /* Returns the root node of a tree data structure representing the directory structure of the input */
    fun buildDirectoryTree(input: List<String>): Dir {
        val rootDir = Dir("/")
        var currentDir: Dir? = rootDir
        val dirMap = mutableMapOf<String, Dir>("/" to rootDir)

        /* build the data structure for the directory tree:
        1) a tree (rootDir) with the directories and files
        2) a map (dirMap) to quickly access a directory by name
         */
        for (item in input) {
            // set new working directory
            if (item.startsWith("$ cd")) {
                val cmdTokens = item.split(' ')
                val dirname = cmdTokens.last()
                currentDir = changeDirectory(dirname, rootDir, currentDir)
                println("Changed directory to ${currentDir!!.path}")
            }
            // no specific action required.
            else if (item.startsWith("$ ls")) {
            }
            // create a new directory
            else if (item.startsWith("dir ")) {
                val cmdTokens = item.split(' ')
                val dirname = cmdTokens.last()
                val newDir = currentDir!!.addChild(Dir(dirname))
                dirMap[newDir.path] = newDir
                println("New directory: ${newDir.path}")
            }
            // only files left (default
            else {
                val file = item.split(" ")
                currentDir!!.addFile(File(file.last(), file.first().toInt()))
            }
        }
        return rootDir
    }

    fun part1(input: List<String>): Long {
        var score = 0L

        /* build the data structure for the directory tree:
        1) a tree (rootDir) with the directories and files
        2) a map (dirMap) to quickly access a directory by name
         */
        val rootDir = buildDirectoryTree(input)
        val dirMap = rootDir.buildMap()

        rootDir.printTree()

        for (dir in dirMap.values) {
            val dirSize = dir.getTreeSize()
            if (dirSize <= 100_000) {
                score += dirSize
            }

        }
        return score
    }

    fun part2(input: List<String>): Long {
        val TOTAL_DISK_SIZE = 70_000_000L
        val NEEDED_DISK_SPACE = 30_000_000L
        var score = TOTAL_DISK_SIZE

        /* build the data structure for the directory tree:
        1) a tree (rootDir) with the directories and files
        2) a map (dirMap) to quickly access a directory by name
         */
        val rootDir = buildDirectoryTree(input)
        val dirMap = rootDir.buildMap()

        val availablDiskSpace = TOTAL_DISK_SIZE - rootDir.getTreeSize()
        val requiredDiskSpace = NEEDED_DISK_SPACE - availablDiskSpace

        // find the smallest directory that still frees enough space.
        for (dir in dirMap.values) {
            val dirSize = dir.getTreeSize()
            if (dirSize > requiredDiskSpace) {
                if (score > dirSize) {
                    score = dirSize
                }
            }
        }
        return score
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    println("Part 1 (Test): ${part1(testInput)}")
    println("Part 2 (Test): ${part2(testInput)}")
    // check(part1(testInput) == 1)

    val input = readInput("Day07")
    // println("Part 1       : ${part1(input)}")
    println("Part 2       : ${part2(input)}")
}
