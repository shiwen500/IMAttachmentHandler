package com.seven.www.imattachmenthandler

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.WindowManager

class InputMethodHelper(private val context: Context, private val rootView: View) {

    private val screenHeight: Int
        get() {
            val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val displayMetrics = DisplayMetrics()
            display.getRealMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }

    private val activity: Activity
        get() = context as Activity

    var imHeight: Int = 0
    var imActive: Boolean = false
    var imChangeListener: IMChangeListener? = null

    companion object {
        const val TAG = "InputMethodHelper"
    }

    init {
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            activity.window.decorView.getWindowVisibleDisplayFrame(rect)
            imHeight = screenHeight - rect.bottom
            val isImShown = imHeight > screenHeight / 4
            if (!isImShown) {
                imHeight = 0
            }

            Log.d(TAG, "active: $isImShown, height: $imHeight")
            if (isImShown != imActive) {
                imActive = isImShown
                imChangeListener?.onIMChanged(imActive, imHeight)
            }
        }
    }

    interface IMChangeListener {
        fun onIMChanged(active: Boolean, imHeight: Int)
    }
}
