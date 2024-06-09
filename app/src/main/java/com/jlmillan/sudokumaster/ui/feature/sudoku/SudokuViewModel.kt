package com.jlmillan.sudokumaster.ui.feature.sudoku

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jlmillan.sudokumaster.logic.SudokuGame

class SudokuViewModel : ViewModel() {
    private val game = SudokuGame()

    private val sudokuBoardLiveData = MutableLiveData<Array<IntArray>>()
    val sudokuBoard: LiveData<Array<IntArray>> get() = sudokuBoardLiveData

    private val gameFinishedLiveData = MutableLiveData<Boolean>()
    val gameFinished: LiveData<Boolean> get() = gameFinishedLiveData

    private val scoreLiveData = MutableLiveData<Int>()
    val score: LiveData<Int> get() = scoreLiveData

    private val emptySpacesLiveData = MutableLiveData<Int>()
    val emptySpaces: LiveData<Int> get() = emptySpacesLiveData

    private val selectedNumberLiveData = MutableLiveData<Int>()
    val selectedNumber: LiveData<Int> get() = selectedNumberLiveData

    init {
        gameFinishedLiveData.value = false
        scoreLiveData.value = 0
        selectedNumberLiveData.value = 1
    }

    fun startGame(emptySpaces: Int) {
        val emptySpacesCount = game.createSudoku(emptySpaces)
        sudokuBoardLiveData.value = game.showSudoku()
        emptySpacesLiveData.value = emptySpacesCount
        scoreLiveData.value = 0
        gameFinishedLiveData.value = false
    }

    fun checkInput(num: Int, row: Int, col: Int) {
        if (sudokuBoardLiveData.value?.get(row)?.get(col) == 0) { // Verifica que la casilla está vacía
            if (game.checkInput(num, row, col)) {
                scoreLiveData.value = (scoreLiveData.value ?: 0) + 10
                val remainingEmptySpaces = (emptySpacesLiveData.value ?: 0) - 1
                emptySpacesLiveData.value = remainingEmptySpaces
                sudokuBoardLiveData.value = game.showSudoku()
                if (remainingEmptySpaces == 0) {
                    gameFinishedLiveData.value = true
                }
            } else {
                scoreLiveData.value = (scoreLiveData.value ?: 0) - 2
            }
        }
    }

    fun solveSudoku() {
        if (game.solveSudoku()) {
            sudokuBoardLiveData.value = game.showSudoku()
            gameFinishedLiveData.value = true
        }
    }

    fun setSelectedNumber(number: Int) {
        selectedNumberLiveData.value = number
    }
}
