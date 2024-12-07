package com.github.kima_mik.days

class Day7 {
    data class Input(val testValue: Long, val equation: List<Long>)


    data class Puzzle1Node(
        val value: Long,
        val depth: Int = 0,
        var sum: Puzzle1Node? = null,
        var multiply: Puzzle1Node? = null
    ) {
        fun findValue(value: Long): Boolean {
            val queue = ArrayDeque<Puzzle1Node>()
            queue.add(this)
            while (queue.isNotEmpty()) {
                val cur = queue.removeLast()
                if (cur.isLeaf() && cur.value == value) {
                    return true
                }
                cur.sum?.let { queue.add(it) }
                cur.multiply?.let { queue.add(it) }
            }

            return false
        }

        private fun isLeaf() = sum == null && multiply == null
    }

    fun puzzle1(input: String): Long {
        var result = 0L

        val rows = extractInput(input)

        rows.forEach { row ->
            val tree = constructPuzzle1Tree(row.equation)
            if (tree.findValue(row.testValue)) {
                result += row.testValue
            }
        }

        return result
    }

    private fun extractInput(input: String): List<Input> {
        return input.trim().lines().map { line ->
            val split = line.split(':')
            val testValue = split[0].toLong()

            val equation = split.last().trim()
                .split(' ').map { it.toLong() }
            Input(testValue, equation)
        }
    }

    private fun constructPuzzle1Tree(equation: List<Long>): Puzzle1Node {
        val queue = ArrayDeque<Puzzle1Node>()
        val root = Puzzle1Node(equation.first())
        queue.addLast(root)
        while (queue.isNotEmpty()) {
            val currentNode = queue.removeFirst()
            val newDepth = currentNode.depth + 1
            if (newDepth == equation.size) {
                continue
            }

            val member = equation[newDepth]
            currentNode.sum = Puzzle1Node(
                value = currentNode.value + member,
                depth = newDepth
            )

            currentNode.multiply = Puzzle1Node(
                value = currentNode.value * member,
                depth = newDepth
            )

            queue.addLast(currentNode.sum!!)
            queue.addLast(currentNode.multiply!!)
        }

        return root
    }

}