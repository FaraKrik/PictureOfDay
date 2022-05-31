package com.example.pictureofdays.ui.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.visible(visible: () -> Boolean): View {
    visibility = if (visible()) {
        View.VISIBLE
    } else {
        View.GONE
    }
    return this
}


fun View.showSnakeBar(text: String, length: Int = Snackbar.LENGTH_INDEFINITE) {
    Snackbar.make(this, text, length).show()
}


fun View.showSnakeBar(stringResource: Int, length: Int = Snackbar.LENGTH_INDEFINITE) {
    Snackbar.make(this, context.getString(stringResource), length).show()
}