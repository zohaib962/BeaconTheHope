package com.beacon.zohaib.beacon.ui.activities;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;

import com.beacon.zohaib.beacon.R;
import com.beacon.zohaib.beacon.adapters.TrackingAdapter;
import com.beacon.zohaib.beacon.datamodels.TrackingModel;
import com.beacon.zohaib.beacon.datamodels.UserDataModel;
import com.beacon.zohaib.beacon.recievers.DeletingAlarmReciever;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TrackingActivity extends AppCompatActivity {

    private static final String ALARM_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mBeaconReference;
    CountDownTimer timer;
    private RecyclerView mRecyclerView;
    private ChildEventListener mChaldEventListener;

    private TrackingAdapter mAdapter;
    List<TrackingModel> mList;
    String username="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);
        setServer();
        mRecyclerView=(RecyclerView)findViewById(R.id.trackingRV);
        setRecycleView();
    }


    public void setServer() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        username=pref.getString("username","");

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mBeaconReference=mFirebaseDatabase.getReference().child("tracking").child(username);
        mList=new ArrayList<>();

        mChaldEventListener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mList.add(dataSnapshot.getValue(TrackingModel.class));

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mBeaconReference.addChildEventListener(mChaldEventListener);


    }
    public void setRecycleView() {


        mAdapter = new TrackingAdapter(mList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);


    }

    public static void remindUser(Context context,String heartRate) {
        // COMPLETED (8) Get the NotificationManager using context.getSystemService
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        // COMPLETED (9) Create a notification channel for Android O devices
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    ALARM_REMINDER_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
        }
        // COMPLETED (10) In the remindUser method use NotificationCompat.Builder to create a notification
        // that:
        // - has a color of R.colorPrimary - use ContextCompat.getColor to get a compatible color
        // - has ic_drink_notification as the small icon
        // - uses icon returned by the largeIcon helper method as the large icon
        // - sets the title to the charging_reminder_notification_title String resource
        // - sets the text to the charging_reminder_notification_body String resource
        // - sets the style to NotificationCompat.BigTextStyle().bigText(text)
        // - sets the notification defaults to vibrate
        // - uses the content intent returned by the contentIntent helper method for the contentIntent
        // - automatically cancels the notification when the notification is clicked
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,ALARM_REMINDER_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle("Heart Rate Warning")
                .setContentText("Warning! Heart Rate is not normal. Current heart rate is "+heartRate)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        "Warning! Heart Rate is not normal. Current heart rate is "+heartRate))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.drawable.notify_b)
                .setAutoCancel(true);
        // COMPLETED (11) If the build version is greater than JELLY_BEAN and lower than OREO,
        // set the notification's priority to PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        // COMPLETED (12) Trigger the notification by calling notify on the NotificationManager.
        // Pass in a unique ID of your choosing for the notification and notificationBuilder.build()
        notificationManager.notify(50, notificationBuilder.build());
    }


    @Override
    protected void onPause() {

        TrackingModel trackingModel = mList.get(mList.size() - 1);
        int heartRate = Integer.valueOf(trackingModel.getHeartrate());
        if (heartRate <= 60 || heartRate >= 100) {
            remindUser(getApplicationContext(), trackingModel.getHeartrate());
            SharedPreferences pref = getSharedPreferences("MyPref", 0);
            String phone = pref.getString("phone", "");
            String username = pref.getString("username", "");
            sendSmsMsgFnc(phone,"Warning! heart of "+username+" is critical.",this);
        }
        super.onPause();
    }

    private static PendingIntent contentIntent(Context context) {
        // COMPLETED (2) Create an intent that opens up the MainActivity
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        // COMPLETED (3) Create a PendingIntent using getActivity that:
        // - Take the context passed in as a parameter
        // - Takes an unique integer ID for the pending intent (you can create a constant for
        //   this integer above
        // - Takes the intent to open the MainActivity you just created; this is what is triggered
        //   when the notification is triggered
        // - Has the flag FLAG_UPDATE_CURRENT, so that if the intent is created again, keep the
        // intent but update the data
        return PendingIntent.getActivity(
                context,
                100,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }


    public static void sendSmsMsgFnc(String mblNumVar, String smsMsgVar,Context context)
    {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
        {
            try
            {
                SmsManager smsMgrVar = SmsManager.getDefault();
                smsMgrVar.sendTextMessage(mblNumVar, null, smsMsgVar, null, null);

            }
            catch (Exception ErrVar)
            {
                ErrVar.printStackTrace();
            }
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                ActivityCompat.requestPermissions((Activity) context,new String[]{android.Manifest.permission.SEND_SMS}, 10);
            }
        }

    }



}
