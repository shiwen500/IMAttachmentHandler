package com.seven.www.imattachmenthandler

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.input_pad.*

class InputPad : Fragment() {

    private val ctx: Context
        get() = activity

    private val im: InputMethodManager
        get() = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    companion object {
        fun newInstance(): InputPad {
            return InputPad()
        }

        const val TAG = "InputPad"
    }

    private lateinit var inputMethodHelper: InputMethodHelper

    private val imActive: Boolean
        get() {
            if (inputMethodHelper == null) {
                return false
            }
            return inputMethodHelper!!.imActive
        }

    private lateinit var ism: InputStateMachine

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.input_pad, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputMethodHelper = InputMethodHelper(activity, this.view)

        ism = InputStateMachine(
                im, inputMethodHelper, activity.window, et_input, btn_list_container, activity.findViewById(R.id.im_container)
        )

        btn_switch.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                when (ism.state) {
                    is InputStateMachine.InputCloseState ->
                            ism.enter(ism.OnlyOpenAttachmentState())
                    is InputStateMachine.OnlyOpenAttachmentState ->
                            ism.enter(ism.OnlyOpenIMState())
                    is InputStateMachine.OnlyOpenIMState ->
                            ism.enter(ism.OnlyOpenAttachmentState())
                }
            }
        })
    }

}