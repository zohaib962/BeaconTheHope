package com.beacon.zohaib.beacon

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun openImapairment(view: View)
    {
        val intent= Intent(this,ImpairmentActivity::class.java)
        startActivity(intent)
    }
    fun openSOS(view: View)
    {
        val intent=Intent(this,SosActivity::class.java)
        startActivity(intent)
    }
    fun openMedication(view: View)
    {
        val intent=Intent(this,ActiveMedication::class.java)
        startActivity(intent)
    }
}
