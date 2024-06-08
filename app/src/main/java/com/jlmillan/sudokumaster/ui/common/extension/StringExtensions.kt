package com.jlmillan.sudokumaster.ui.common.extension

fun String.validateEmail(): Boolean{
    val email =("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex())
    return this.matches(email)
}