package com.jlmillan.sudokumaster.ui.common.extension

fun String.isEmailFormat(): Boolean {
    val emailRegex = Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})")
    return matches(emailRegex)
}