package com.beacon.zohaib.beacon.ui.activities;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.beacon.zohaib.beacon.R;

public class VisionActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {


    private TextToSpeech repeatTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);

        repeatTTS = new TextToSpeech(this, this);

    }

    public void openTextDetectActivity(View view)
    {
        Intent intent=new Intent(VisionActivity.this, TextDetect.class);
        startActivity(intent);
    }

    public void openObjectDetectActivity(View view)
    {

        repeatTTS.speak("swipe up to open camera",
                TextToSpeech.QUEUE_FLUSH, null);
        Intent intent=new Intent(VisionActivity.this, ObjectDetect.class);
        startActivity(intent);
    }

    @Override
    public void onInit(int i) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
