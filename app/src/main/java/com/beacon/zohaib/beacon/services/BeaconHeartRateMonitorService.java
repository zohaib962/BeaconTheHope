package com.beacon.zohaib.beacon.services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;

import com.beacon.zohaib.beacon.datamodels.TrackingModel;
import com.beacon.zohaib.beacon.db.MeadicationDb;
import com.beacon.zohaib.beacon.db.MedicationTable;
import com.beacon.zohaib.beacon.db.MedicationTimeJoin;
import com.beacon.zohaib.beacon.db.TimeTable;
import com.beacon.zohaib.beacon.ui.activities.MainActivity;
import com.beacon.zohaib.beacon.ui.activities.Medication;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.beacon.zohaib.beacon.ui.activities.TrackingActivity.remindUser;
import static com.beacon.zohaib.beacon.ui.activities.TrackingActivity.sendSmsMsgFnc;


/**
 * Created by Zohaib on 4/22/2018.
 */

public class BeaconHeartRateMonitorService extends JobService {

    List<TrackingModel> mList;

   @Override
    public boolean onStartJob(JobParameters job) {

        Log.d("MyJob", "onStartJob: ");

        mList=MainActivity.Companion.getMList();
        if(mList.size()>0) {
            listSize();

            TrackingModel trackingModel = mList.get(mList.size() - 1);
            int heartRate = Integer.valueOf(trackingModel.getHeartrate());
            if (heartRate <= 60 || heartRate >= 100) {
                remindUser(getApplicationContext(), trackingModel.getHeartrate());
                SharedPreferences pref = getSharedPreferences("MyPref", 0);
                String phone = pref.getString("phone", "");
                String username = pref.getString("username", "");
                jobFinished(job, true);
            }
        }
        return true;
    }


    public void listSize()
    {

        Log.d("Mylist", String.valueOf(mList.size()));
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }


}
