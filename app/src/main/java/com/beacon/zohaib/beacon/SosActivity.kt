package com.beacon.zohaib.beacon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class SosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sos)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId)
        {
            android.R.id.home -> {finish()
                //NavUtils.navigateUpFromSameTask(this)
                return true}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }
}
