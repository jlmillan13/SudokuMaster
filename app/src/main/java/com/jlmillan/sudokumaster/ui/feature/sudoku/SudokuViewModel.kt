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

    init {
        gameFinishedLiveData.value = false
        scoreLiveData.value = 0
    }

    fun startGame(difficulty: Int) {
        val emptySpaces = game.createSudoku(difficulty)
        sudokuBoardLiveData.value = game.showSudoku()
        emptySpacesLiveData.value = emptySpaces
        scoreLiveData.value = 0
        gameFinishedLiveData.value = false
    }

    fun checkInput(num: Int, row: Int, col: Int) {
        if (game.checkInput(num, row, col)) {
            scoreLiveData.value = (scoreLiveData.value ?: 0) + 10
            emptySpacesLiveData.value = (emptySpacesLiveData.value ?: 0) - 1
            sudokuBoardLiveData.value = game.showSudoku()
            if (emptySpacesLiveData.value == 0) {
                gameFinishedLiveData.value = true
            }
        } else {
            scoreLiveData.value = (scoreLiveData.value ?: 0) - 2
        }
    }
}
