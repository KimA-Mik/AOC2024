package com.github.kima_mik.days

import com.github.kima_mik.util.CharField
import com.github.kima_mik.util.Direction

private const val OBSTACLE_CELL = '#'
private const val FREE_CELL = '.'
private const val BOX_CELL = 'O'
private const val LEFT_BOX_CELL = '['
private const val RIGHT_BOX_CELL = ']'
private const val ROBOT_CELL = '@'

class Day15 : AOCDay(15) {
    private data class Warehouse(val field: CharField, val moves: List<Direction>)


    override fun puzzle1(input: String): Int {
        val warehouse = extractInput(input)
//        println("Initial state:\n${warehouse.field}")
        for (move in warehouse.moves) {
            simulateMove(move, warehouse.field)
//            println("Move $move:\n${warehouse.field}")
        }

        return warehouse.field.calculateSumOfGpsCoordinates()
    }

    private fun extractInput(input: String): Warehouse {
        val lines = input.trim().lines()

        val split = lines.indexOfFirst { it.isBlank() }
        val fieldLines = lines.subList(0, split)
        val field = CharField(fieldLines.joinToString("").toCharArray(), fieldLines[0].length, fieldLines.size)

        val moves = mutableListOf<Direction>()
        val movesLines = lines.subList(split + 1, lines.size)
        for (movesLine in movesLines) {
            for (c in movesLine) {
                moves.add(Direction.fromChar(c))
            }
        }

        return Warehouse(field, moves)
    }

    private fun simulateMove(move: Direction, field: CharField) {
        val (robotX, robotY) = field.robotPosition()
        val newX = robotX + move.xOffset
        val newY = robotY + move.yOffset

        when (field[newX, newY]) {
            OBSTACLE_CELL -> return
            FREE_CELL -> {
                field[newX, newY] = ROBOT_CELL
                field[robotX, robotY] = FREE_CELL
            }

            BOX_CELL -> tryMoveBoxes(
                field = field,
                move = move,
                robotX = robotX,
                robotY = robotY,
            )

            else -> throw Exception("Unexpected field '$field'")
        }
    }

    private fun tryMoveBoxes(field: CharField, move: Direction, robotX: Int, robotY: Int) {
        var xCursor = robotX + move.xOffset * 2
        var yCursor = robotY + move.yOffset * 2
        while (field[xCursor, yCursor] != FREE_CELL) {
            if (field[xCursor, yCursor] == OBSTACLE_CELL) {
                return
            }

            xCursor += move.xOffset
            yCursor += move.yOffset
        }

        while (field[xCursor, yCursor] != ROBOT_CELL) {
            field[xCursor, yCursor] = field[xCursor - move.xOffset, yCursor - move.yOffset]
            xCursor -= move.xOffset
            yCursor -= move.yOffset
        }

        field[robotX, robotY] = FREE_CELL
    }

    private fun CharField.robotPosition() = indexToCoordinates(cells.indexOfFirst { it == ROBOT_CELL })

    private fun CharField.calculateSumOfGpsCoordinates(): Int {
        var result = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (this[x, y] == BOX_CELL ||
                    this[x, y] == LEFT_BOX_CELL
                ) {
                    result += 100 * y + x
                }
            }
        }

        return result
    }

    override fun puzzle2(input: String): Int {
        val warehouse = extractInput(input)
        var buffer1 = warehouse.field.widen()
        var buffer2 = CharField(buffer1.width, buffer1.height)
//        println("Initial state:\n$buffer1")

        for (move in warehouse.moves) {
            simulateWideMove(move, buffer1, buffer2)
            val temp = buffer1
            buffer1 = buffer2
            buffer2 = temp
//            println("Move $move:\n$buffer1")
        }

        return buffer1.calculateSumOfGpsCoordinates()
    }

    private fun CharField.widen(): CharField {
        val newWidth = width * 2
        val newField = CharField(CharArray(newWidth * height), newWidth, height)

        for (y in 0 until height) {
            for (x in 0 until width) {
                when (this[x, y]) {
                    OBSTACLE_CELL -> {
                        newField[x * 2, y] = OBSTACLE_CELL
                        newField[x * 2 + 1, y] = OBSTACLE_CELL
                    }

                    FREE_CELL -> {
                        newField[x * 2, y] = FREE_CELL
                        newField[x * 2 + 1, y] = FREE_CELL
                    }

                    BOX_CELL -> {
                        newField[x * 2, y] = LEFT_BOX_CELL
                        newField[x * 2 + 1, y] = RIGHT_BOX_CELL
                    }

                    ROBOT_CELL -> {
                        newField[x * 2, y] = ROBOT_CELL
                        newField[x * 2 + 1, y] = FREE_CELL
                    }

                    else -> throw Exception("Unknown cell [$x, $y]: ${this[x, y]}")
                }
            }
        }

        return newField
    }

    private fun simulateWideMove(move: Direction, source: CharField, target: CharField) {
        val (robotX, robotY) = source.robotPosition()
        val newX = robotX + move.xOffset
        val newY = robotY + move.yOffset
        source.cells.copyInto(target.cells)

        when (source[newX, newY]) {
            OBSTACLE_CELL -> return
            FREE_CELL -> {
                target[newX, newY] = ROBOT_CELL
                target[robotX, robotY] = FREE_CELL
            }

            LEFT_BOX_CELL -> tryMoveWideBoxes(
                source = source,
                target = target,
                move = move,
                robotX = robotX,
                robotY = robotY,
            )

            RIGHT_BOX_CELL -> tryMoveWideBoxes(
                source = source,
                target = target,
                move = move,
                robotX = robotX,
                robotY = robotY,
            )

            else -> throw Exception("Unexpected wide field '$source'")
        }
    }

    private data class MoveTreeNode(
        val x: Int,
        val y: Int,
        val children: MutableList<MoveTreeNode> = mutableListOf()
    ) {
        fun contains(x: Int, y: Int): Boolean = traverse { node -> node.x == x && node.y == y }


        fun meetObstacle(move: Direction, field: CharField): Boolean = traverse { node ->
            val newX = node.x + move.xOffset
            val newY = node.y + move.yOffset

            return@traverse field[newX, newY] == OBSTACLE_CELL
        }

        private fun traverse(action: (MoveTreeNode) -> Boolean): Boolean {
            val deque = ArrayDeque<MoveTreeNode>()
            deque.addLast(this)

            while (deque.isNotEmpty()) {
                val node = deque.removeLast()
                if (action(node)) {
                    return true
                }

                deque.addAll(node.children)
            }

            return false
        }
    }

    private fun tryMoveWideBoxes(source: CharField, target: CharField, move: Direction, robotX: Int, robotY: Int) {
        if (move == Direction.LEFT || move == Direction.RIGHT) {
            tryMoveBoxes(target, move, robotX, robotY)
            return
        }

        val tree = constructMoveTree(source, move, robotX, robotY)
        if (tree.meetObstacle(move, source)) {
            return
        }

        val deque = ArrayDeque<MoveTreeNode>()
        deque.addLast(tree)
        val list = mutableListOf<Pair<Int, Int>>()

        while (deque.isNotEmpty()) {
            val node = deque.removeFirst()
            deque.addAll(node.children)
            list.add(node.x to node.y)
        }

        list.sortBy {
            if (move == Direction.BOTTOM) -it.second
            else it.second
        }

        for (node in list) {
            target[node.first, node.second + move.yOffset] = source[node.first, node.second]
            target[node.first, node.second] = FREE_CELL
        }
    }

    private fun constructMoveTree(field: CharField, move: Direction, robotX: Int, robotY: Int): MoveTreeNode {
        val root = MoveTreeNode(robotX, robotY)
        val buildDeque = ArrayDeque<MoveTreeNode>()
        buildDeque.addLast(root)

        while (buildDeque.isNotEmpty()) {
            val node = buildDeque.removeLast()
            var newX = node.x + move.xOffset
            val newY = node.y + move.yOffset

            if (field[newX, newY] != LEFT_BOX_CELL && field[newX, newY] != RIGHT_BOX_CELL) continue
            if (root.contains(newX, newY)) continue

            var child = MoveTreeNode(newX, newY)
            node.children.add(child)
            buildDeque.add(child)
            when (field[newX, newY]) {
                LEFT_BOX_CELL -> newX += 1
                RIGHT_BOX_CELL -> newX -= 1
            }

            if (field[newX, newY] != LEFT_BOX_CELL && field[newX, newY] != RIGHT_BOX_CELL) continue
            if (root.contains(newX, newY)) continue

            child = MoveTreeNode(newX, newY)
            node.children.add(child)
            buildDeque.add(child)
        }

        return root
    }
}