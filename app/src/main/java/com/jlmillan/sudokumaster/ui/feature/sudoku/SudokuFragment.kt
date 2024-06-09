package com.jlmillan.sudokumaster.ui.feature.sudoku

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jlmillan.sudokumaster.R
import com.jlmillan.sudokumaster.data.common.CacheManager
import com.jlmillan.sudokumaster.databinding.FragmentSudokuBinding
import com.jlmillan.sudokumaster.logic.SudokuBoardView
import com.jlmillan.sudokumaster.ui.common.extension.show

class SudokuFragment : Fragment() {

    private lateinit var binding: FragmentSudokuBinding
    private lateinit var sudokuBoard: SudokuBoardView
    private var selectedRow: Int? = null
    private var selectedCol: Int? = null
    private val viewModel: SudokuViewModel by viewModels()
    private val args: SudokuFragmentArgs by navArgs()

    private val handler = Handler(Looper.getMainLooper())
    private var seconds = 0
    private var isRunning = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSudokuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sudokuBoard = binding.sudokuBoard
        sudokuBoard.setOnCellTapListener { row, col ->
            if (viewModel.sudokuBoard.value?.get(row)?.get(col) == 0) {
                selectedRow = row
                selectedCol = col
                sudokuBoard.selectCell(row, col)
            }
        }

        setupNumberBar()
        runTimer()

        viewModel.sudokuBoard.observe(viewLifecycleOwner, Observer { board ->
            sudokuBoard.updateBoard(board)
        })

        viewModel.score.observe(viewLifecycleOwner, Observer { score ->
            binding.scoreTextView.text = "Score: $score"
        })

        viewModel.gameFinished.observe(viewLifecycleOwner, Observer { isFinished ->
            binding.gameFinishedTextView.show(isFinished)
            if (isFinished) {
                isRunning = false
                saveGameResultIfBetter(args.emptySpaces)
                findNavController().navigate(R.id.action_sudokuFragment_to_mainFragment)
            }
        })

        // Iniciar el juego con el nivel seleccionado
        viewModel.startGame(args.emptySpaces)
    }

    private fun runTimer() {
        handler.post(object : Runnable {
            override fun run() {
                val minutes = seconds / 60
                val secs = seconds % 60
                val time = String.format("%02d:%02d", minutes, secs)
                binding.timeTextView.text = "Time: $time"
                if (isRunning) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        })
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
                        if (viewModel.sudokuBoard.value?.get(row)?.get(col) != 0) {
                            selectedRow = null
                            selectedCol = null
                            sudokuBoard.deselectCell()
                        }
                    }
                }
            }
        }
    }

    private fun saveGameResultIfBetter(difficulty: Int) {
        val currentPoints = viewModel.score.value ?: 0
        val currentTime = seconds

        CacheManager.saveGameResultIfBetter(requireContext(), currentPoints, currentTime, difficulty)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isRunning = false
    }
}
