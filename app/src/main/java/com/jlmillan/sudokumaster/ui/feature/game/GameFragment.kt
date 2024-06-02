package com.jlmillan.sudokumaster.ui.feature.game


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jlmillan.sudokumaster.ui.theme.SudokuTheme

class GameFragment : Fragment() {

    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val userId = arguments?.getString("userId") ?: ""
        gameViewModel.initGameModel(userId)

        return ComposeView(requireContext()).apply {
            setContent {
                SudokuTheme {
                    GameScreen(
                        userId = userId,
                        gameViewModel = gameViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun GameScreen(
    userId: String,
    gameViewModel: GameViewModel
) {
    var puzzle by remember { mutableStateOf(gameViewModel.getPuzzle()) }
    var selectedRow by remember { mutableStateOf(-1) }
    var selectedCol by remember { mutableStateOf(-1) }
    var gameCompleted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        gameViewModel.getGameProgress(userId) { progress ->
            progress?.let {
                puzzle = it.puzzle.map { row -> row.toIntArray() }.toTypedArray()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            gameViewModel.saveGameProgress()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (gameCompleted) {
            Text(text = "¡Felicidades, has completado el Sudoku!")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                gameViewModel.generatePuzzle()
                puzzle = gameViewModel.getPuzzle()
                selectedRow = -1
                selectedCol = -1
                gameCompleted = false
            }) {
                Text(text = "Nuevo Juego")
            }
        } else {
            PuzzleView(puzzle, selectedRow, selectedCol) { row, col ->
                selectedRow = row
                selectedCol = col
            }
            Spacer(modifier = Modifier.height(16.dp))
            GameKeyPad { number ->
                if (selectedRow != -1 && selectedCol != -1) {
                    gameViewModel.setTile(selectedRow, selectedCol, number)
                    puzzle = gameViewModel.getPuzzle()
                    if (gameViewModel.checkPuzzle()) {
                        gameCompleted = true
                    }
                }
            }
        }
    }
}

