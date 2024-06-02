package com.jlmillan.sudokumaster.ui.feature.game

import kotlin.random.Random

class Game {
    private val puzzle = Array(9) { IntArray(9) }
    private val solution = Array(9) { IntArray(9) }

    init {
        generatePuzzle()
    }

    fun getTile(row: Int, col: Int): Int = puzzle[row][col]

    fun setTile(row: Int, col: Int, value: Int) {
        puzzle[row][col] = value
    }

    fun generatePuzzle() {
        // Clear the puzzle
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                puzzle[i][j] = 0
            }
        }

        // Generate a fully solved Sudoku
        solveSudoku(solution)

        // Copy the solution to the puzzle
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                puzzle[i][j] = solution[i][j]
            }
        }

        // Remove some numbers to create the puzzle
        val removalCount = Random.nextInt(30, 40)
        for (i in 0 until removalCount) {
            val row = Random.nextInt(9)
            val col = Random.nextInt(9)
            puzzle[row][col] = 0
        }
    }

    private fun isSafe(row: Int, col: Int, num: Int): Boolean {
        for (i in 0 until 9) {
            if (puzzle[row][i] == num || puzzle[i][col] == num) return false
        }
        val startRow = row - row % 3
        val startCol = col - col % 3
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                if (puzzle[i + startRow][j + startCol] == num) return false
            }
        }
        return true
    }

    private fun solveSudoku(board: Array<IntArray>, n: Int = 9): Boolean {
        var row = -1
        var col = -1
        var isEmpty = true
        for (i in 0 until n) {
            for (j in 0 until n) {
                if (board[i][j] == 0) {
                    row = i
                    col = j
                    isEmpty = false
                    break
                }
            }
            if (!isEmpty) break
        }
        if (isEmpty) return true
        for (num in 1..9) {
            if (isSafe(row, col, num)) {
                board[row][col] = num
                if (solveSudoku(board, n)) return true
                board[row][col] = 0
            }
        }
        return false
    }

    fun checkPuzzle(): Boolean {
        for (i in 0 until 9) {
            for (j in 0 until 9) {
                if (puzzle[i][j] != solution[i][j]) return false
            }
        }
        return true
    }

    fun getPuzzle(): Array<IntArray> = puzzle
}
