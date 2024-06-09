package com.jlmillan.sudokumaster.ui.feature.sudoku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.jlmillan.sudokumaster.R
import com.jlmillan.sudokumaster.databinding.FragmentSudokuBinding
import com.jlmillan.sudokumaster.logic.SudokuBoardView
import com.jlmillan.sudokumaster.ui.common.extension.show

class SudokuFragment : Fragment() {

    private lateinit var binding: FragmentSudokuBinding
    private lateinit var sudokuBoard: SudokuBoardView
    private var selectedRow: Int? = null
    private var selectedCol: Int? = null
    private val viewModel: SudokuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSudokuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sudokuBoard = binding.sudokuBoard
        sudokuBoard.setOnCellTapListener { row, col ->
            selectedRow = row
            selectedCol = col
        }

        setupNumberBar()

        viewModel.sudokuBoard.observe(viewLifecycleOwner, Observer { board ->
            sudokuBoard.updateBoard(board)
        })

        viewModel.score.observe(viewLifecycleOwner, Observer { score ->
            binding.scoreTextView.text = "Score: $score"
        })

        viewModel.gameFinished.observe(viewLifecycleOwner, Observer { isFinished ->
            if (isFinished) {
                binding.gameFinishedTextView.show(isFinished)
                findNavController().navigate(R.id.action_sudokuFragment_to_mainFragment)
            } else {
                binding.gameFinishedTextView.show(false)
            }
        })

        // Obtener el nivel de dificultad del bundle y iniciar el juego
        val emptySpaces = arguments?.getInt("emptySpaces") ?: 35
        viewModel.startGame(emptySpaces)
    }

    private fun setupNumberBar() {
        val numberBar = binding.numberBar
        for (i in 0 until numberBar.childCount) {
            val button = numberBar.getChildAt(i) as Button
            button.setOnClickListener {
                val number = button.text.toString().toInt()
                selectedRow?.let { row ->
                    selectedCol?.let { col ->
                        viewModel.checkInput(number, row, col)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
