package com.beacon.zohaib.beacon.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.beacon.zohaib.beacon.R

class ImpairmentActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_impairment)

        /*
         So ashmed of my bad programming this is how u access static members:D
         */
        MainActivity.mList
    }

    fun openSpeechActivity(view: View)
    {
        val intent=Intent(this, SpeechActivity::class.java)
        startActivity(intent)
    }

    fun openTextDetect(view: View)
    {

        val intent=Intent(this, VisionActivity::class.java)
        startActivity(intent)
    }

    fun openHearingActivity(view: View)
    {
        val intent=Intent(this, HearingActivity::class.java)
        startActivity(intent)
    }

    fun openMentalActivity(view: View)
    {
        val intent=Intent(this, MentalActivity::class.java)
        startActivity(intent)
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
