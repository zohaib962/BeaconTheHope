package com.beacon.zohaib.beacon.recievers;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.widget.Toast;

import com.beacon.zohaib.beacon.R;
import com.beacon.zohaib.beacon.db.MeadicationDb;
import com.beacon.zohaib.beacon.ui.activities.ActiveMedication;
import com.beacon.zohaib.beacon.ui.activities.CurrentMed;
import com.beacon.zohaib.beacon.ui.activities.MainActivity;
import com.beacon.zohaib.beacon.ui.activities.Medication;
import com.beacon.zohaib.beacon.ui.activities.SOSActivity;
import com.beacon.zohaib.beacon.utils.Constants;

/**
 * Created by Zohaib on 3/4/2018.
 */

public class AlarmReciever extends BroadcastReceiver
{

    private static final String ALARM_REMINDER_NOTIFICATION_CHANNEL_ID = "reminder_notification_channel";
    @Override
    public void onReceive(Context context, Intent intent)
    {


        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Constants.ringtone = RingtoneManager.getRingtone(context, alarmUri);
        Constants.ringtone.play();
        remindUser(context);

        SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
        String phone=pref.getString("phone","");
        String username=pref.getString("username","");
        sendSmsMsgFnc(phone,"It's time for "+username+" to take their medicine. Please make sure they take it on time.",context);


    }
    public static void remindUser(Context context) {
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
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(contentIntent(context))
                .setDeleteIntent(cancelIntent(context))
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

   private static PendingIntent cancelIntent(Context context)
    {
        Intent intent = new Intent(context, DeletingAlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        return pendingIntent;
    }


    private static PendingIntent contentIntent(Context context) {
        // COMPLETED (2) Create an intent that opens up the MainActivity
        Intent startActivityIntent = new Intent(context, CurrentMed.class);
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


    void sendSmsMsgFnc(String mblNumVar, String smsMsgVar,Context context)
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
