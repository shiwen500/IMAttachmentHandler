package com.seven.www.imattachmenthandler

import android.view.View
import android.view.ViewTreeObserver

/*
 * Copyright (C) 2018, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * Test.java
 *
 * Description : 类的描述
 *
 * Author ChenShiWen, Created at 18-7-13
 *
 * Ver 1.0, 18-7-13, ChenShiWen, Create file
 */
class Test {

    fun f() {

        val view = View(null)
        view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                view.viewTreeObserver.removeOnPreDrawListener(this)
                return false
            }
        })
    }
}
