package com.beacon.zohaib.beacon.ui.activities;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beacon.zohaib.beacon.R;
import com.google.android.gms.vision.CameraSource;

import java.util.ArrayList;
import java.util.Locale;

public class HearingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hearing);

        //for back navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void openHearingAidActivity(View view)
    {
        Intent intent=new Intent(HearingActivity.this,HearingAidActivity.class);
        startActivity(intent);
    }
    public void openDeafActivity(View view)
    {
        Intent intent=new Intent(HearingActivity.this,DeafActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
