package com.github.kima_mik.days

class Day7 {
    data class Input(val testValue: Long, val equation: List<Long>)


    data class PuzzleNode(
        val value: Long,
        val depth: Int = 0,
        var sum: PuzzleNode? = null,
        var multiply: PuzzleNode? = null,
        var concatenation: PuzzleNode? = null,
    ) {
        fun findValue(value: Long): Boolean {
            val queue = ArrayDeque<PuzzleNode>()
            queue.add(this)
            while (queue.isNotEmpty()) {
                val cur = queue.removeLast()
                if (cur.isLeaf() && cur.value == value) {
                    return true
                }
                cur.sum?.let { queue.add(it) }
                cur.multiply?.let { queue.add(it) }
                cur.concatenation?.let { queue.add(it) }
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

    private fun constructPuzzle1Tree(equation: List<Long>): PuzzleNode {
        val queue = ArrayDeque<PuzzleNode>()
        val root = PuzzleNode(equation.first())
        queue.addLast(root)
        while (queue.isNotEmpty()) {
            val currentNode = queue.removeFirst()
            val newDepth = currentNode.depth + 1
            if (newDepth == equation.size) {
                continue
            }

            val member = equation[newDepth]
            currentNode.sum = PuzzleNode(
                value = currentNode.value + member,
                depth = newDepth
            )

            currentNode.multiply = PuzzleNode(
                value = currentNode.value * member,
                depth = newDepth
            )

            queue.addLast(currentNode.sum!!)
            queue.addLast(currentNode.multiply!!)
        }

        return root
    }

    fun puzzle2(input: String): Long {
        var result = 0L

        val rows = extractInput(input)
        rows.forEach { row ->
            val tree = constructPuzzle2Tree(row.equation)
            if (tree.findValue(row.testValue)) {
                result += row.testValue
            }
        }

        return result
    }

    private fun constructPuzzle2Tree(equation: List<Long>): PuzzleNode {
        val queue = ArrayDeque<PuzzleNode>()
        val root = PuzzleNode(equation.first())
        queue.addLast(root)
        while (queue.isNotEmpty()) {
            val currentNode = queue.removeFirst()
            val newDepth = currentNode.depth + 1
            if (newDepth == equation.size) {
                continue
            }

            val member = equation[newDepth]
            currentNode.sum = PuzzleNode(
                value = currentNode.value + member,
                depth = newDepth
            )

            currentNode.multiply = PuzzleNode(
                value = currentNode.value * member,
                depth = newDepth
            )

            currentNode.concatenation = PuzzleNode(
                value = concatenateLongs(currentNode.value, member),
                depth = newDepth
            )

            queue.addLast(currentNode.sum!!)
            queue.addLast(currentNode.multiply!!)
            queue.addLast(currentNode.concatenation!!)
        }

        return root
    }

    private fun concatenateLongs(left: Long, right: Long): Long {
        var shifter = right
        var leftShifted = left * 10L
        while (shifter / 10 > 0) {
            shifter /= 10
            leftShifted *= 10L
        }

        return leftShifted + right
    }
}