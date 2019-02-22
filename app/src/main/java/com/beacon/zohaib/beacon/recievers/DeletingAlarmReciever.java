package com.beacon.zohaib.beacon.recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.beacon.zohaib.beacon.utils.Constants;

/**
 * Created by Zohaib on 3/17/2018.
 */

public class DeletingAlarmReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Constants.ringtone.stop();
        Toast.makeText(context,"Rigntone stoped",Toast.LENGTH_SHORT).show();
    }
}
