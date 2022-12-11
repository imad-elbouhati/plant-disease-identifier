package com.imadev.plantindentifier.utils

import android.app.Activity
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.imadev.plantindentifier.MainActivity


fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .show()
}


fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

