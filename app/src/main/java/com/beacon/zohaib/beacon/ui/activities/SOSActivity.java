package com.beacon.zohaib.beacon.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.beacon.zohaib.beacon.Manifest;
import com.beacon.zohaib.beacon.R;

/**
 * Created by Zohaib on 4/13/2018.
 */

public class SOSActivity extends AppCompatActivity {

    private TextView callTV;
    private static Intent phoneCallIntent;
    CountDownTimer timer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sos);
        callTV=(TextView)findViewById(R.id.callTV);
         timer = new CountDownTimer(5000, 5000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                String phone=pref.getString("phone","");
                if(!phone.equals("")) {
                    final String permissionToCall = android.Manifest.permission.CALL_PHONE;
                    phoneCallIntent = new Intent(Intent.ACTION_CALL);
                    phoneCallIntent.setData(Uri.parse("tel:"+phone)); //Uri.parse("tel:your number")
                    if (ActivityCompat.checkSelfPermission(SOSActivity.this, permissionToCall) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SOSActivity.this, new String[]{permissionToCall}, 1);
                        return;
                    }
                    startActivity(phoneCallIntent);
                }
            }
        };
        timer.start();

    }

    public void call(View view) {

        timer.cancel();
        finish();
    }

    @Override
    public void onBackPressed() {
        timer.cancel();

        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            final int permissionsLength = permissions.length;
            for (int i = 0; i < permissionsLength; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    startActivity(phoneCallIntent);
                }
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }


}