package com.jlmillan.sudokumaster.ui.common

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jlmillan.sudokumaster.databinding.ActivityMainBinding
import com.jlmillan.sudokumaster.ui.common.extension.show

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpToolbar()
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding.mainToolbar)
    }

    fun showToolbar(visible: Boolean) {
        binding.mainToolbar.show(visible)
    }

    fun setToolbarTitle(title: String) {
        binding.mainToolbarTitle.text = title
    }

    fun showLoading(loading: Boolean) {
        binding.mainLoading.root.show(loading)
    }

    override fun onStart() {
        super.onStart()
        Log.e("LIFECYCLE", "onStart")
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
