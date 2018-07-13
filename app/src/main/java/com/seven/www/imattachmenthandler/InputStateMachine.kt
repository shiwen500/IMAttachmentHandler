package com.seven.www.imattachmenthandler

import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.ResultReceiver
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

class InputStateMachine(
        val imm: InputMethodManager,
        val imHelper: InputMethodHelper,
        val window: Window,
        val etInput: EditText,
        val attachment: View,
        val inputPad: View
) {

    private val handler = Handler()

    inner class InputCloseState : InputState {


        override fun enter() {
            // close im, close attachment
            imm.hideSoftInputFromWindow(etInput.applicationWindowToken, 0)
            attachment.visibility = View.GONE
        }
    }

    inner class OnlyOpenIMState : InputState {

        override fun enter() {
            // close attachment
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            handler.postDelayed({
                imm.showSoftInput(etInput, InputMethodManager.SHOW_FORCED, object : ResultReceiver(handler) {
                    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                        if (resultCode == InputMethodManager.RESULT_SHOWN) {

                        }
                        attachment.visibility = View.GONE
                        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    }
                })
            }, 50)
        }
    }

    inner class OnlyOpenAttachmentState: InputState {

        override fun enter() {
            // close im
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

            var draw = false

            handler.postDelayed({

                attachment.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        if (!draw) {
                            return false
                        } else {
                            attachment.viewTreeObserver.removeOnPreDrawListener(this)
                            return true
                        }
                    }
                })

                attachment.visibility = View.VISIBLE

                imm.hideSoftInputFromWindow(etInput.applicationWindowToken, 0, object : ResultReceiver(handler) {
                    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                        if (resultCode == InputMethodManager.RESULT_HIDDEN) {

                        }
                        draw = true
                        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    }
                })
            }, 50)
        }
    }

    var state: InputState = InputCloseState()

    fun enter(state: InputState) {
        state.enter()
        this.state = state
    }

    fun hideSoftInputFromWindow(windowToken: IBinder, flags: Int, listener: InputMethodHelper.IMChangeListener) {
        imHelper.imChangeListener = listener
        imm.hideSoftInputFromWindow(windowToken, flags)
    }

    fun showSoftInput(view: View, flags: Int, listener: InputMethodHelper.IMChangeListener) {
        imHelper.imChangeListener = listener
        imm.showSoftInput(view, flags)
    }
}
