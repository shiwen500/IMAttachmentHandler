package com.seven.www.imattachmenthandler

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG_INPUT_PAD = "tag_im"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showIM()
    }

    private fun showIM() {
        var imFragment = fragmentManager.findFragmentByTag(TAG_INPUT_PAD) as IMFragment?
        if (imFragment == null) {
            imFragment = IMFragment.newInstance()
            fragmentManager.beginTransaction()
                    .add(R.id.main, imFragment, TAG_INPUT_PAD)
                    .commit()
            return
        }
        fragmentManager.beginTransaction().show(imFragment).commit()
    }
}
