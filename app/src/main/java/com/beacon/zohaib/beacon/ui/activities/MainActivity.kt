package com.beacon.zohaib.beacon.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.beacon.zohaib.beacon.R
import com.beacon.zohaib.beacon.datamodels.TrackingModel
import com.beacon.zohaib.beacon.utils.ReminderUtilities
import com.google.firebase.database.*
import java.util.ArrayList

public class MainActivity : AppCompatActivity() {

    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var mBeaconReference: DatabaseReference
    private lateinit var mChaldEventListener: ChildEventListener
    internal var username: String? = ""

    companion object {
       public var mList=ArrayList<TrackingModel>()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var pref=applicationContext.getSharedPreferences("MyPref", 0)
        var uname=pref.getString("username","")
        var upass=pref.getString("password","")

        if(uname.equals("") && upass.equals("") )
        {

            val intent= Intent(this, LOginActivity::class.java)
            startActivity(intent)

            return;
        }

      setServer()
        ReminderUtilities.scheduleChargingReminder(this);
    }

    fun openImapairment(view: View)
    {
        val intent= Intent(this, ImpairmentActivity::class.java)
        startActivity(intent)
    }
    fun openSOS(view: View)
    {
        val intent=Intent(this, SOSActivity::class.java)
        startActivity(intent)
    }
    fun openMedication(view: View)
    {
        val intent=Intent(this, ActiveMedication::class.java)
        startActivity(intent)
    }
    fun openTracking(view: View)
    {
        val intent=Intent(this, TrackingActivity::class.java)
        startActivity(intent)
    }
    override fun onBackPressed() {
        this.finishAffinity()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mai_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId)
        {
            R.id.id_logout ->{
                var pref=applicationContext.getSharedPreferences("MyPref", 0)

                var edit=pref.edit()
                edit.clear()
                edit.commit()

                val intent= Intent(this, LOginActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    fun setServer() {

       val pref = applicationContext.getSharedPreferences("MyPref", 0)
       username = pref.getString("username", "")

        mFirebaseDatabase = FirebaseDatabase.getInstance()

        mBeaconReference = mFirebaseDatabase.getReference().child("tracking").child(username)
        mList = ArrayList()

        mChaldEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot?, s: String?) {
                dataSnapshot?.getValue(TrackingModel::class.java)?.let {
                    mList.add(dataSnapshot.getValue(TrackingModel::class.java)!!)
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, s: String) {

            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {

            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, s: String) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        mBeaconReference.addChildEventListener(mChaldEventListener)


    }


}


