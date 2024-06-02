package com.jlmillan.sudokumaster.ui.feature.game


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private var game: Game = Game()

    var gameModel: GameModel? = null

    fun initGameModel(userId: String) {
        gameModel = GameModel(
            progress = GameProgress(userId = userId),
            stats = GameStats(userId = userId)
        )
    }

    fun saveGameProgress() {
        viewModelScope.launch {
            gameModel?.let {
                it.progress = GameProgress(
                    userId = it.progress.userId,
                    puzzle = game.getPuzzle().map { row -> row.toList() },
                    selectedRow = -1,
                    selectedCol = -1
                )
                it.saveGameProgress()
            }
        }
    }

    fun getGameProgress(userId: String, onComplete: (GameProgress?) -> Unit) {
        viewModelScope.launch {
            val progress = gameModel?.getGameProgress(userId)
            progress?.let {
                it.puzzle.map { row -> row.toIntArray() }.forEachIndexed { rowIndex, row ->
                    row.forEachIndexed { colIndex, value ->
                        game.setTile(rowIndex, colIndex, value)
                    }
                }
            }
            onComplete(progress)
        }
    }

    fun saveGameStats() {
        viewModelScope.launch {
            gameModel?.saveGameStats()
        }
    }

    fun getUserStats(userId: String, onComplete: (List<GameStats>) -> Unit) {
        viewModelScope.launch {
            val stats = gameModel?.getUserStats(userId)
            onComplete(stats ?: listOf())
        }
    }

    fun generatePuzzle() {
        game.generatePuzzle()
    }

    fun getPuzzle(): Array<IntArray> = game.getPuzzle()

    fun setTile(row: Int, col: Int, value: Int) {
        game.setTile(row, col, value)
    }

    fun checkPuzzle(): Boolean = game.checkPuzzle()
}
