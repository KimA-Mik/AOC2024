package com.github.kima_mik.days

import java.util.*


private const val EMPTY_BLOCK = -1

class Day9 : AOCDay(9) {
    override fun puzzle1(input: String): Long {
        val (fs, _) = extractFileSystem(input)
        simpleDefragment(fs)
        return fs.calculateFsCheckSum()
    }

    private fun extractFileSystem(input: String): Pair<IntArray, SortedMap<Int, Int>> {
        val compressedMap = input.trim().map { it.digitToInt() }
        val fsSize = compressedMap.sum()

        val fs = IntArray(fsSize) { 0 }
        val emptySpaceIndex = sortedMapOf<Int, Int>()

        var fsOffset = 0
        var isFile = true
        var currentFileId = 0
        for (size in compressedMap) {
            if (isFile) {
                fs.writeNTimes(fsOffset, size, currentFileId)
                currentFileId += 1
            } else {
                fs.writeNTimes(fsOffset, size, EMPTY_BLOCK)
                emptySpaceIndex[fsOffset] = size
            }
            fsOffset += size
            isFile = !isFile
        }

        return fs to emptySpaceIndex
    }

    private fun simpleDefragment(fs: IntArray) {
        var firstFreeSpace = fs.finedNextFreeBlock(0)
        for (i in fs.lastIndex downTo 0) {
            if (i <= firstFreeSpace) break
            val curId = fs[i]
            if (curId == EMPTY_BLOCK) continue

            fs[firstFreeSpace] = curId
            fs[i] = EMPTY_BLOCK
            firstFreeSpace = fs.finedNextFreeBlock(firstFreeSpace)
        }
    }

    private fun IntArray.calculateFsCheckSum(): Long {
        var result = 0L
        for (i in indices) {
            val curId = this[i]
            if (curId == EMPTY_BLOCK) continue
            result += curId * i
        }
        return result
    }

    private fun IntArray.finedNextFreeBlock(offset: Int): Int {
        for (i in offset..lastIndex) {
            if (this[i] == EMPTY_BLOCK) {
                return i
            }
        }

        return -1
    }

    private fun IntArray.writeNTimes(offset: Int, count: Int, value: Int) {
        repeat(count) {
            this[offset + it] = value
        }
    }

    private fun IntArray.fsToString(): String {
        val sb = StringBuilder()
        for (i in this) {
            if (i == EMPTY_BLOCK) {
                sb.append('.')
            } else {
                sb.append(i)
            }
        }

        return sb.toString()
    }

    override fun puzzle2(input: String): Long {
        val (fs, emptySpaceIndex) = extractFileSystem(input)
        extendedDefragment(fs, emptySpaceIndex)
        return fs.calculateFsCheckSum()
    }

    private fun extendedDefragment(fs: IntArray, emptySpaceIndex: SortedMap<Int, Int>) {
        var i = fs.lastIndex
        while (i >= 0) {
            val fileId = fs[i]
            if (fileId == EMPTY_BLOCK) {
                i -= 1
                continue
            }

            val fileSize = fs.calculateFileSize(i)
            val firstFreeSpace = emptySpaceIndex.firstEnoughFreeSpace(fileSize)
            if (firstFreeSpace < 0 || firstFreeSpace >= i) {
                i -= fileSize
                continue
            }

            fs.writeNTimes(firstFreeSpace, fileSize, fileId)
            fs.writeNTimes(i - fileSize + 1, fileSize, EMPTY_BLOCK)

            val newSize = emptySpaceIndex[firstFreeSpace]!! - fileSize
            emptySpaceIndex.remove(firstFreeSpace)
            if (newSize > 0) {
                emptySpaceIndex[firstFreeSpace + fileSize] = newSize
            }

            i -= fileSize
        }
    }

    private fun IntArray.calculateFileSize(rightOffset: Int): Int {
        val id = this[rightOffset]
        if (id == EMPTY_BLOCK) return -1

        var result = 1
        var i = rightOffset - 1
        while (i >= 0 && this[i] == id) {
            result += 1
            i -= 1
        }

        return result
    }

    private fun Map<Int, Int>.firstEnoughFreeSpace(size: Int): Int {
        for (record in this) {
            if (record.value >= size) {
                return record.key
            }
        }

        return -1
    }
}