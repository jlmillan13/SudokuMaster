package com.jlmillan.sudokumaster.logic

import kotlin.random.Random

class SudokuGame {
    private val sudoku = Array(9) { IntArray(9) }
    private val sudokuUnsolved = Array(9) { IntArray(9) }

    fun createSudoku(difficulty: Int): Int {
        fillDiagonal()
        fillRemaining(0, 3)
        removeKDigits(difficulty)
        return countEmptySpaces()
    }

    fun showSudoku(): Array<IntArray> {
        return sudokuUnsolved
    }

    fun checkInput(num: Int, row: Int, col: Int): Boolean {
        return if (num in 1..9 && row in 0..8 && col in 0..8 && sudoku[row][col] == num) {
            sudokuUnsolved[row][col] = num
            true
        } else {
            false
        }
    }

    private fun fillDiagonal() {
        for (i in 0..8 step 3) {
            fillBox(i, i)
        }
    }

    private fun unUsedInBox(rowStart: Int, colStart: Int, num: Int): Boolean {
        for (i in 0..2)
            for (j in 0..2)
                if (sudoku[rowStart + i][colStart + j] == num)
                    return false
        return true
    }

    private fun fillBox(row: Int, col: Int) {
        var num: Int
        for (i in 0..2) {
            for (j in 0..2) {
                do {
                    num = randomGenerator(9)
                } while (!unUsedInBox(row, col, num))
                sudoku[row + i][col + j] = num
            }
        }
    }

    private fun randomGenerator(num: Int): Int {
        return Random.nextInt(num) + 1
    }

    private fun checkIfSafe(i: Int, j: Int, num: Int): Boolean {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i - i % 3, j - j % 3, num))
    }

    private fun unUsedInRow(i: Int, num: Int): Boolean {
        for (j in 0..8)
            if (sudoku[i][j] == num)
                return false
        return true
    }

    private fun unUsedInCol(j: Int, num: Int): Boolean {
        for (i in 0..8)
            if (sudoku[i][j] == num)
                return false
        return true
    }

    private fun fillRemaining(i: Int, j: Int): Boolean {
        var i = i
        var j = j
        if (j >= 9 && i < 8) {
            i++
            j = 0
        }
        if (i >= 9 && j >= 9)
            return true

        if (i < 3) {
            if (j < 3)
                j = 3
        } else if (i < 9 - 3) {
            if (j == (i / 3) * 3)
                j += 3
        } else {
            if (j == 9 - 3) {
                i++
                j = 0
                if (i >= 9)
                    return true
            }
        }

        for (num in 1..9) {
            if (checkIfSafe(i, j, num)) {
                sudoku[i][j] = num
                if (fillRemaining(i, j + 1))
                    return true
                sudoku[i][j] = 0
            }
        }
        return false
    }

    private fun removeKDigits(difficulty: Int) {
        val count = difficulty
        var k = count
        while (k != 0) {
            val i = randomGenerator(9) - 1
            val j = randomGenerator(9) - 1
            if (sudokuUnsolved[i][j] != 0) {
                k--
                sudokuUnsolved[i][j] = 0
            }
        }
    }

    private fun countEmptySpaces(): Int {
        var count = 0
        for (i in 0..8) {
            for (j in 0..8) {
                if (sudokuUnsolved[i][j] == 0) {
                    count++
                }
            }
        }
        return count
    }
}
