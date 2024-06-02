package com.jlmillan.sudokumaster.ui.common.extension

import android.view.View

fun View.show(visible: Boolean, hideType: Int = View.GONE) {
    visibility = if (visible) View.VISIBLE else hideType
}