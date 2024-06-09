package com.jlmillan.sudokumaster.ui.common.extension

import android.view.View

fun View.show(isVisible: Boolean, hideType: Int = View.GONE) {
    visibility = if (isVisible) View.VISIBLE else hideType
}