package com.seven.www.imattachmenthandler

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.seven.www.imattachmenthandler.wx.WXIMActivity

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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menu.addSubMenu(0, 0, 0, "WXIM")

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == 0) {
            val wxim = Intent(this, WXIMActivity::class.java)
            startActivity(wxim)
        }

        return super.onOptionsItemSelected(item)
    }
}
