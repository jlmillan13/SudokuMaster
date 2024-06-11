package com.jlmillan.sudokumaster.ui.common

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.jlmillan.sudokumaster.R
import com.jlmillan.sudokumaster.databinding.ActivityMainBinding
import com.jlmillan.sudokumaster.logic.SudokuGame
import com.jlmillan.sudokumaster.ui.common.extension.show
import com.jlmillan.sudokumaster.ui.feature.sudoku.SudokuViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        Log.e("LIFECYCLE", "onStart")
    }

    fun showLoading(loading: Boolean) {
        binding?.mainLoading?.root?.show(loading)
    }

    override fun onResume() {
        super.onResume()
        Log.e("LIFECYCLE", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e("LIFECYCLE", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.e("LIFECYCLE", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e("LIFECYCLE", "onDestroy")
    }
}
