package com.beacon.zohaib.beacon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //for back navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    fun openLogIn(view: View)
    {
        val intent=Intent(this,LoginActivity::class.java)
        startActivity(intent);
    }
}
