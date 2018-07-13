package com.seven.www.imattachmenthandler

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout

class IMView : LinearLayout {
    constructor(context: Context?) : super(context) {

    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {

    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {

    }

    lateinit var chatList: View
    lateinit var inputContainer: View
    lateinit var attachment: View

    override fun onFinishInflate() {
        super.onFinishInflate()
        chatList = findViewById(R.id.chat_list)
        inputContainer = findViewById(R.id.input_container)
        attachment = findViewById(R.id.attachment_container)
    }

    private var originHeight: Int = 0

    fun fixedHeight() {
        if (measuredHeight != 0) {
            originHeight = layoutParams.height
            layoutParams.height = measuredHeight
        }
    }

    fun fixedHeightWithoutAttachment() {
        if (measuredHeight != 0) {
            originHeight = layoutParams.height
            layoutParams.height = measuredHeight - attachment.measuredHeight
        }
    }

    fun restoreHeight() {
        if (originHeight != 0) {
            layoutParams.height = originHeight
        }
    }
}