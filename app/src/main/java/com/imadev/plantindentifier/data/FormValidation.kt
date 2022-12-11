package com.imadev.plantindentifier.data

import android.widget.EditText

object FormValidation {

    fun areEditTextsEmpty(vararg editTexts: EditText): Boolean {
        for (editText in editTexts) {
            if (editText.text.isEmpty()) {
                return true
            }
        }
        return false
    }

    fun isValidEmail(email: String): Boolean {
        val regex = Regex(pattern = "^[A-Za-z0-9+_.-]+@(.+)\$")
        return email.matches(regex)
    }


    fun arePasswordsIdentical(password1: String, password2: String): Boolean {
        return password1 == password2
    }
}