package com.seven.www.imattachmenthandler

import android.view.Window
import android.view.inputmethod.InputMethodManager

interface InputState {

    fun enter()

    fun name(): String {
        return this.javaClass.simpleName
    }
}
