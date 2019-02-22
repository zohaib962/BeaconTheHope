package com.beacon.zohaib.beacon.ui.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.beacon.zohaib.beacon.R;
import com.beacon.zohaib.beacon.adapters.ActiveMedAdapter;
import com.beacon.zohaib.beacon.adapters.CurrentMedAdapter;
import com.beacon.zohaib.beacon.db.MeadicationDb;
import com.beacon.zohaib.beacon.db.MedicationTable;
import com.beacon.zohaib.beacon.utils.Constants;

import java.util.Calendar;
import java.util.List;

public class CurrentMed extends AppCompatActivity {

    Calendar rightNow = Calendar.getInstance();
    long currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
    private RecyclerView recyclerView;
    private CurrentMedAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_med);
        //Constants.ringtone.stop();
        recyclerView = (RecyclerView) findViewById(R.id.currMedRV);
        new TimeQuereysAsyncTask().execute();
    }

    public void takeMed(View view)
    {
        finish();
    }

    private class TimeQuereysAsyncTask extends AsyncTask<Void, Void, List<MedicationTable>> {

        @Override
        protected List<MedicationTable> doInBackground(Void... voids) {


            List<MedicationTable> list1 = MeadicationDb.newInstance(CurrentMed.this).getMedicationTimeJoin().getCurrMedList(currentHour);



            if(list1!=null) {
                Log.d("list2", "Size " + list1.size());

                return list1;

            }
            return null;
        }

        @Override
        protected void onPostExecute(List<MedicationTable> medicationTables) {
            super.onPostExecute(medicationTables);

            if(medicationTables!=null)
            {
                mAdapter = new CurrentMedAdapter(medicationTables);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);

            }

        }
    }



}
