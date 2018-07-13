package com.seven.www.imattachmenthandler

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG_INPUT_PAD = "tag_input_pad"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showInputPad()
    }

    private fun showInputPad() {
        var inputPad = fragmentManager.findFragmentByTag(TAG_INPUT_PAD) as InputPad?
        if (inputPad == null) {
            inputPad = InputPad.newInstance()
            fragmentManager.beginTransaction()
                    .add(R.id.im_container, inputPad, TAG_INPUT_PAD)
                    .commit()
            return
        }
        fragmentManager.beginTransaction().show(inputPad).commit()
    }
}
