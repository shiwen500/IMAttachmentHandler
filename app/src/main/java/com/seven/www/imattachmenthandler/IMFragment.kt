package com.seven.www.imattachmenthandler

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.fragment_im.*
import kotlinx.android.synthetic.main.input_pad.*

class IMFragment : Fragment() {

    companion object {
        const val TAG = "IMFragment"
        fun newInstance(): IMFragment = IMFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_im, container, false)
    }


    private lateinit var imHelper: InputMethodHelper
    private lateinit var ism: InputStateMachine


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imHelper = InputMethodHelper(activity, im_view)
        ism = InputStateMachine(
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager,
                imHelper,
                activity.window,
                et_input,
                attachment_container,
                btn_switch,
                im_view
        )

        btn_switch.setOnClickListener { v ->
            when (ism.state) {
                is InputStateMachine.InputCloseState -> {
                    ism.enter(ism.OnlyOpenAttachmentState())
                }

                is InputStateMachine.OnlyOpenAttachmentState -> {
                    ism.enter(ism.OnlyOpenIMState())
                }
                is InputStateMachine.OnlyOpenIMState -> {
                    ism.enter(ism.OnlyOpenAttachmentState())
                }
            }
        }

        et_input.setOnTouchListener { v, event ->
            ism.enter(ism.OnlyOpenIMState())
            false
        }
    }
}