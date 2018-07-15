package com.seven.www.imattachmenthandler.wx

import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import com.seven.www.imattachmenthandler.IMView
import com.seven.www.imattachmenthandler.InputState
import com.seven.www.imattachmenthandler.R

class WxInputStateMachine(
        val imm: InputMethodManager,
        val window: Window,
        val imView: IMView,
        val btnVoice: ImageButton,
        val etInput: EditText,
        val btnRecord: Button,
        val btnEmoji: ImageButton,
        val btnOther: ImageButton,
        val otherInterface: OtherInterface
) {

    val handler = Handler()

    inner class Voice : InputState {

        override fun enter() {
            if (state is Voice) {
                return
            }

            btnVoice.setImageResource(R.drawable.keyboard)
            etInput.visibility = View.GONE
            btnRecord.visibility = View.VISIBLE
            otherInterface.hide()
            btnEmoji.setImageResource(R.drawable.emoji)


            imm.hideSoftInputFromWindow(etInput.applicationWindowToken, 0)
        }

        override fun name(): String  = "语音输入"
    }

    inner class Emoji : InputState {

        override fun enter() {
            if (state is Emoji) {
                return
            }

            btnVoice.setImageResource(R.drawable.voice)
            etInput.visibility = View.VISIBLE
            btnRecord.visibility = View.GONE
            btnEmoji.setImageResource(R.drawable.keyboard)

            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            imView.fixedHeight()
            imm.hideSoftInputFromWindow(etInput.applicationWindowToken, 0)

            handler.postDelayed({
                imView.restoreHeight()
                otherInterface.showEmoji()
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }, 100)
        }

        override fun name(): String {
            return "emoji"
        }
    }

    inner class Other : InputState {

        override fun enter() {
            if (state is Other) {
                return
            }

            btnVoice.setImageResource(R.drawable.voice)
            etInput.visibility = View.VISIBLE
            btnRecord.visibility = View.GONE

            btnEmoji.setImageResource(R.drawable.emoji)

            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            imView.fixedHeight()
            imm.hideSoftInputFromWindow(etInput.applicationWindowToken, 0)

            handler.postDelayed({
                imView.restoreHeight()
                otherInterface.showAttachment()
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            }, 100)
        }

        override fun name(): String {
            return "Other"
        }
    }

    inner class Keyboard : InputState {

        override fun enter() {
            if (state is Keyboard) {
                return
            }

            btnVoice.setImageResource(R.drawable.voice)
            etInput.visibility = View.VISIBLE
            btnRecord.visibility = View.GONE
            btnEmoji.setImageResource(R.drawable.emoji)

            otherInterface.hide()
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
            imView.fixedHeightWithoutAttachment()

            imm.showSoftInput(etInput, InputMethodManager.SHOW_FORCED)

            handler.postDelayed({
                window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                imView.restoreHeight()
            }, 100)
        }

        override fun name(): String {
            return "键盘输入"
        }
    }

    var state: InputState = Keyboard()

    fun enter(newState: InputState) {
        newState.enter()
        state = newState
    }

    interface OtherInterface {
        fun showEmoji()
        fun showAttachment()
        fun hide()
    }
}