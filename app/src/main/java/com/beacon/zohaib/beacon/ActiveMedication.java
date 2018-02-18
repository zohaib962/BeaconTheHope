package com.beacon.zohaib.beacon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ActiveMedication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_medication);
    }

    public void addMedication(View view)
    {
        Intent intent=new Intent(ActiveMedication.this,Medication.class);
        startActivity(intent);
    }
}
