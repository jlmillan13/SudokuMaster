package com.jlmillan.sudokumaster.ui.feature.sudoku


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels


class SudokuFragment : Fragment() {

    private val viewModel: SudokuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SudokuGameScreen(viewModel)
            }
        }
    }
}

@Composable
fun SudokuGameScreen(viewModel: SudokuViewModel) {
    // Implementación de la UI del juego usando Compose
    val sudokuBoard by viewModel.sudokuBoard.observeAsState()
    val score by viewModel.score.observeAsState(0)
    val gameFinished by viewModel.gameFinished.observeAsState(false)

    // Aquí puedes diseñar la UI usando Compose, por ejemplo:
    Column {
        Text(text = "Score: $score")
        if (gameFinished) {
            Text(text = "Game Over!")
        } else {
            SudokuBoard(sudokuBoard) { num, row, col ->
                viewModel.checkInput(num, row, col)
            }
        }
    }
}

@Composable
fun SudokuBoard(
    board: Array<IntArray>?,
    onCellClicked: (num: Int, row: Int, col: Int) -> Unit
) {
    // Renderizar el tablero de Sudoku aquí
    board?.let {
        // Implementa el renderizado del tablero usando composables
        for (row in it.indices) {
            Row {
                for (col in it[row].indices) {
                    val cellValue = it[row][col]
                    SudokuCell(cellValue) { num ->
                        onCellClicked(num, row, col)
                    }
                }
            }
        }
    }
}

@Composable
fun SudokuCell(value: Int, onClick: (Int) -> Unit) {
    // Renderizar cada celda del Sudoku aquí
    Box(
        modifier = Modifier
            .size(40.dp)
            .border(1.dp, Color.Black)
            .clickable { onClick(value) },
        contentAlignment = Alignment.Center
    ) {
        Text(text = if (value == 0) "" else value.toString())
    }
}
