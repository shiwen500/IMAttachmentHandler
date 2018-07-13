package com.seven.www.imattachmenthandler

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.ResultReceiver
import android.util.Log
import android.util.Property
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.input_pad.*

class InputStateMachine(
        val imm: InputMethodManager,
        val imHelper: InputMethodHelper,
        val window: Window,
        val etInput: EditText,
        val attachment: View,
        val switchBtn: Button,
        val imView: IMView
) {

    val handler = Handler()

    inner class InputCloseState : InputState {


        override fun enter() {
            // close im, close attachment
            imm.hideSoftInputFromWindow(etInput.applicationWindowToken, 0)
            attachment.visibility = View.GONE
        }
    }

    inner class OnlyOpenIMState : InputState {

        override fun enter() {

            if (state is OnlyOpenIMState) {
                return
            }

            switchBtn.setText(R.string.btn_input_show_button_list)

            // close attachment
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            imView.fixedHeightWithoutAttachment()
            attachment.visibility = View.GONE

            imm.showSoftInput(etInput, InputMethodManager.SHOW_FORCED)
            handler.postDelayed({
                imView.restoreHeight()
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }, 100)
        }
    }

    inner class OnlyOpenAttachmentState: InputState {

        override fun enter() {

            if (state is OnlyOpenAttachmentState) {
                return
            }

            switchBtn.setText(R.string.btn_input_show_im)
            // close im
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            imView.fixedHeight()

            imm.hideSoftInputFromWindow(etInput.applicationWindowToken, 0)

            handler.postDelayed({
                attachment.visibility = View.VISIBLE
                imView.restoreHeight()
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }, 100)
        }
    }

    var state: InputState = InputCloseState()

    fun enter(state: InputState) {
        state.enter()
        this.state = state
    }
}
