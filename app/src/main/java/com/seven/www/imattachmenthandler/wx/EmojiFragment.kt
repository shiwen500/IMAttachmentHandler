package com.seven.www.imattachmenthandler.wx

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.seven.www.imattachmenthandler.R

class EmojiFragment : Fragment() {

    companion object {
        fun newInstance(): EmojiFragment {
            return EmojiFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_emoji, container, false)
    }
}