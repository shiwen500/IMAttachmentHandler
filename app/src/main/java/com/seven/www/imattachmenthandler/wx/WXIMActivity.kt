package com.seven.www.imattachmenthandler.wx

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class WXIMActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fragmentManager.beginTransaction()
                .add(android.R.id.content, WxIMFragment(), "WXIMActivity")
                .commit()
    }
}