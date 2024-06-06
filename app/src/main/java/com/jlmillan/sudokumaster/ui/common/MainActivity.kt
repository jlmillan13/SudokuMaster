package com.jlmillan.sudokumaster.ui.common

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.jlmillan.sudokumaster.R
import com.jlmillan.sudokumaster.databinding.ActivityMainBinding
import com.jlmillan.sudokumaster.ui.common.extension.show

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setUpToolbar()
        setContentView(binding?.root)
    }

    private fun setUpToolbar() {
        setSupportActionBar(binding?.mainToolbar)
    }

    fun showToolbar(visible: Boolean) {
        binding?.mainToolbar?.show(visible)
    }

    fun setToolbarTitle(title: String) {
        binding?.mainToolbarTitle?.text = title
    }

    fun showLoading(loading: Boolean) {
        binding?.mainLoading?.root?.show(loading)
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
        binding = null
        super.onDestroy()
        Log.e("LIFECYCLE", "onDestroy")
    }
}