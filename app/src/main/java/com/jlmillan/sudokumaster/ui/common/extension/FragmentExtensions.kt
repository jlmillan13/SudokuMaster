package com.jlmillan.sudokumaster.ui.common.extension

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, text, duration).show()
}