package com.seven.www.imattachmenthandler.wx

import android.app.Fragment
import android.app.FragmentTransaction
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.seven.www.imattachmenthandler.R
import kotlinx.android.synthetic.main.fragment_im.*
import kotlinx.android.synthetic.main.input_pad_wx.*

class WxIMFragment : Fragment(), WxInputStateMachine.OtherInterface {

    companion object {
        const val EMOJI_TAG = "emoji"
        const val ATTACHMENT_TAG = "attachment"
    }

    override fun showEmoji() {
        attachment_container.visibility = View.VISIBLE

        var emojiFragment = childFragmentManager.findFragmentByTag(EMOJI_TAG) as EmojiFragment?
        val attachmentFragment = childFragmentManager.findFragmentByTag(ATTACHMENT_TAG)

        var fragmentTransaction: FragmentTransaction
        if (emojiFragment == null) {
            emojiFragment = EmojiFragment()
            fragmentTransaction = childFragmentManager.beginTransaction()
                    .add(R.id.attachment_container, emojiFragment, EMOJI_TAG)
        } else {
            fragmentTransaction = childFragmentManager.beginTransaction()
                    .show(emojiFragment)

        }

        if (attachmentFragment != null) {
            fragmentTransaction.hide(attachmentFragment)
        }
        fragmentTransaction.commit()
    }

    override fun showAttachment() {
        attachment_container.visibility = View.VISIBLE

        var attachmentFragment = childFragmentManager.findFragmentByTag(ATTACHMENT_TAG) as AttachmentFragment?
        val emojiFragment = childFragmentManager.findFragmentByTag(EMOJI_TAG)

        var fragmentTransaction: FragmentTransaction
        if (attachmentFragment == null) {
            attachmentFragment = AttachmentFragment()
            fragmentTransaction = childFragmentManager.beginTransaction()
                    .add(R.id.attachment_container, attachmentFragment, ATTACHMENT_TAG)
        } else {
            fragmentTransaction = childFragmentManager.beginTransaction()
                    .show(attachmentFragment)

        }
        if (emojiFragment != null) {
            fragmentTransaction.hide(emojiFragment)
        }

        fragmentTransaction.commit()
    }

    override fun hide() {
        attachment_container.visibility = View.GONE
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_im_wx, container, false)
    }

    lateinit var wxISM: WxInputStateMachine

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wxISM = WxInputStateMachine(
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager,
                activity.window,
                im_view,
                btn_voice,
                et_input,
                btn_record,
                btn_emoji,
                btn_other,
                this
        )

        btn_voice.setOnClickListener {
            if (wxISM.state is WxInputStateMachine.Voice) {
                wxISM.enter(wxISM.Keyboard())
            } else {
                wxISM.enter(wxISM.Voice())
            }
        }

        btn_emoji.setOnClickListener {
            if (wxISM.state is WxInputStateMachine.Emoji) {
                wxISM.enter(wxISM.Keyboard())
            } else {
                wxISM.enter(wxISM.Emoji())
            }
        }

        btn_other.setOnClickListener {
            if (wxISM.state is WxInputStateMachine.Other) {
                wxISM.enter(wxISM.Keyboard())
            } else {
                wxISM.enter(wxISM.Other())
            }
        }

        et_input.setOnTouchListener { v, event ->
            wxISM.enter(wxISM.Keyboard())
            false
        }
    }
}