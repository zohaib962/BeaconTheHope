package com.beacon.zohaib.beacon.ui.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.beacon.zohaib.beacon.R;
import com.beacon.zohaib.beacon.db.MeadicationDb;
import com.beacon.zohaib.beacon.db.MedicationTable;
import com.beacon.zohaib.beacon.db.MedicationTimeJoin;
import com.beacon.zohaib.beacon.db.TimeTable;
import com.beacon.zohaib.beacon.recievers.AlarmReciever;
import com.beacon.zohaib.beacon.utils.Constants;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Medication extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TimePickerDialog picker;

    Spinner reminderSpinner;
    Button saveBtn;
    List<AlarmManager> mAlarmManager;
    AlarmManager mManager;
    EditText medicationTypeET;
    EditText dosageET;

    boolean flag=false;
    SharedPreferences pref;

    private int count=0;

    long time1;
    long time2;
    long time3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);

        Calendar calendar = Calendar.getInstance();
        String  value=String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));

        medicationTypeET = (EditText) findViewById(R.id.medicationTypeET);
        dosageET = (EditText) findViewById(R.id.dosageET);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        mAlarmManager = new ArrayList<AlarmManager>();
        reminderSpinner = (Spinner) findViewById(R.id.reminderSpinner);
        mManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        reminderSpinner.setOnItemSelectedListener(this);
        List<String> categories = new ArrayList<String>();
        categories.add("Once a day");
        categories.add("Twice a day");
        categories.add("Thrice a day");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        reminderSpinner.setAdapter(dataAdapter);

        new TimeQuereysAsyncTask().execute();

    }

    public void checkTimeInDatabase() {
        pref = getApplicationContext().getSharedPreferences(Constants.sharedPrefernceKey, 0);
        boolean check=pref.getBoolean(Constants.sharedPreferenceDataKey,false);
        if (check==false) {
            SharedPreferences.Editor edit = pref.edit();
            edit.putBoolean(Constants.sharedPreferenceDataKey, true);
            edit.commit();

            time1=9;
            time2=14;
            time3=21;
            TimeTable tTobj = new TimeTable(time1);
            TimeTable tTobj2 = new TimeTable(time2);
            TimeTable tTobj3 = new TimeTable(time3);

            new TimeAsyncTask().execute(tTobj,tTobj2,tTobj3);


        }
        else
        {

            new TimeQuereysAsyncTask().execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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

    boolean flag2=false;
    boolean flag3=false;
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        SharedPreferences mPref=getSharedPreferences("count",0);
        SharedPreferences.Editor editor=mPref.edit();
//        mAlarmManager.clear();
        switch (i) {
            case 0:
                count=1;
                editor.putInt("count",1);
                editor.commit();
               // if(flag==false)
                //setOnce();
                break;
            case 1:
                count=2;

                editor.putInt("count",2);
                editor.commit();
                //if(flag2==false)
                //setTwice();
                break;
            case 2:
                count=3;

                editor.putInt("count",3);
                editor.commit();
                //if(flag3==false)
                //setThrice();
                break;
            default:
                //setOnce();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void setOnce() {

        long t1;
        mAlarmManager.clear();
        flag=true;
        mAlarmManager.add((AlarmManager) getSystemService(ALARM_SERVICE));
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 18);
//        calendar.set(Calendar.MINUTE, 00);
        Intent intent = new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);


// Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
      //  calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE,00);
        calendar.set(Calendar.MILLISECOND,0);
        if(calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }



        //mManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10000, pendingIntent);

    //    mAlarmManager.get(0).setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10000, pendingIntent);


        mAlarmManager.get(0).setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }


    public void setTwice() {
        flag2=true;
        mAlarmManager.clear();

        mAlarmManager.add((AlarmManager) getSystemService(ALARM_SERVICE));

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 18);
//        calendar.set(Calendar.MINUTE, 00);
        Intent intent = new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE,00);
        if(calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }


        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(System.currentTimeMillis());
        calendar2.set(Calendar.HOUR_OF_DAY, 21);
        calendar2.set(Calendar.MINUTE,00);
        if(calendar2.before(Calendar.getInstance())) {
            calendar2.add(Calendar.DATE, 1);
        }


        mAlarmManager.get(0).setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  AlarmManager.INTERVAL_DAY,pendingIntent);

        Intent intent1 = new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 1, intent1, 0);


        mAlarmManager.get(0).setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis() ,AlarmManager.INTERVAL_DAY, pendingIntent1);

    }


    public void setThrice() {
        flag3=true;
        mAlarmManager.clear();

        mAlarmManager.add((AlarmManager) getSystemService(ALARM_SERVICE));

//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 18);
//        calendar.set(Calendar.MINUTE, 00);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE,00);

        if(calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }


        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTimeInMillis(System.currentTimeMillis());
        calendar2.set(Calendar.HOUR_OF_DAY, 14);
        calendar2.set(Calendar.MINUTE,00);

        if(calendar2.before(Calendar.getInstance())) {
            calendar2.add(Calendar.DATE, 1);
        }


        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTimeInMillis(System.currentTimeMillis());
        calendar3.set(Calendar.HOUR_OF_DAY, 21);
        calendar3.set(Calendar.MINUTE,00);

        if(calendar3.before(Calendar.getInstance())) {
            calendar3.add(Calendar.DATE, 1);
        }


        Intent intent = new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        mAlarmManager.get(0).setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,  pendingIntent);

        Intent intent1 = new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent1 = PendingIntent.getBroadcast(this, 1, intent1, 0);


        mAlarmManager.get(0).setRepeating(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(),AlarmManager.INTERVAL_DAY , pendingIntent1);


        Intent intent2 = new Intent(this, AlarmReciever.class);
        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(this, 2, intent2, 0);


        mAlarmManager.get(0).setRepeating(AlarmManager.RTC_WAKEUP, calendar3.getTimeInMillis(),AlarmManager.INTERVAL_DAY , pendingIntent2);
//        mAlarmManager.get(2).set(AlarmManager.RTC_WAKEUP, calendar3.getTimeInMillis() , pendingIntent3);

    }

    public void saveMedication(View view) {

        if(count==1)
        {
            setOnce();
        }
        else if(count==2)
        {
            setTwice();
        }
        else if(count==3)
        {
            setThrice();
        }
        else {
            Toast.makeText(this,"alarm not set",Toast.LENGTH_SHORT).show();
        }

        checkTimeInDatabase();
        String s=medicationTypeET.getText().toString();
       if (!s.isEmpty() && !dosageET.getText().toString().isEmpty() ) {
            MedicationTable mTobj = new MedicationTable(medicationTypeET.getText().toString(),
                    Integer.valueOf(dosageET.getText().toString()));

        //    MeadicationDb.newInstance(Medication.this).getMedicationDao().insertMed(mTobj);
            new MedicationTimeAsyncTask().execute(mTobj);

//            int id = MeadicationDb.newInstance(Medication.this).
//                    getMedicationDao().
//                    getId(medicationTypeET.getText().toString(), Integer.valueOf(dosageET.getText().toString()));
            new ReturnIdAsyncTask().execute();
        }

        finish();


    }

    public void saveMed(int id)
    {
        switch (reminderSpinner.getSelectedItemPosition()) {
            case 0:
                MedicationTimeJoin mtjobj = new MedicationTimeJoin(id, 1);

                new MedicationTimeJoinAsyncTask().execute(mtjobj);
                break;
            case 1:

                MedicationTimeJoin mtjobj1 = new MedicationTimeJoin(id, 1);

                MedicationTimeJoin mtjobj2 = new MedicationTimeJoin(id, 2);

                new MedicationTimeJoinAsyncTask().execute(mtjobj1,mtjobj2);
                break;
            case 2:

                MedicationTimeJoin mjobj1 = new MedicationTimeJoin(id, 1);
                MedicationTimeJoin mjobj2 = new MedicationTimeJoin(id, 2);
                MedicationTimeJoin mjobj3 = new MedicationTimeJoin(id, 3);


                new MedicationTimeJoinAsyncTask().execute(mjobj1,mjobj2,mjobj3);

                break;
            default:

                Toast.makeText(this, "data not entered", Toast.LENGTH_SHORT).show();
        }

        new TimeQuereysAsyncTask().execute();

    }
    private class MedicationTimeJoinAsyncTask extends AsyncTask<MedicationTimeJoin, Void, Void> {

        @Override
        protected Void doInBackground(MedicationTimeJoin... medicationTimeJoins) {

            MeadicationDb.newInstance(Medication.this).getMedicationTimeJoin().insert(medicationTimeJoins);
            return null;
        }

    }

    private class MedicationTimeAsyncTask extends AsyncTask<MedicationTable, Void, Void> {


        @Override
        protected Void doInBackground(MedicationTable... medicationTables) {

            MeadicationDb.newInstance(Medication.this).getMedicationDao().insertMed(medicationTables);
            return null;
        }
    }

    private class TimeAsyncTask extends AsyncTask<TimeTable, Void, Void> {


        @Override
        protected Void doInBackground(TimeTable... timeTables) {

            MeadicationDb.newInstance(Medication.this).getTimeDao().insert(timeTables);

            List<TimeTable> list = MeadicationDb.newInstance(Medication.this).getTimeDao().getAllTime();

            Log.d("Zohaib : ", "Size " + list.size());

            return null;
        }
    }
    private class TimeQuereysAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {


           // MeadicationDb.newInstance(Medication.this).getTimeDao().delTime(3);
            List<TimeTable> list = MeadicationDb.newInstance(Medication.this).getTimeDao().getAllTime();
            List<MedicationTable> list1 = MeadicationDb.newInstance(Medication.this).getMedicationDao().getAllMeds();
            List<MedicationTimeJoin> list3 = MeadicationDb.newInstance(Medication.this).getMedicationTimeJoin().getAllMednTime();

//            TimeTable obj=list.get(2);
            Log.d("list1", "Size " + list.size());

            if(list1!=null&&list3!=null) {
                Log.d("list2", "Size " + list1.size());

                Log.d("list3", "Size " + list3.size());
            }
            return null;
        }
    }

    private class ReturnIdAsyncTask extends AsyncTask<Void, Void, Integer>
    {


        @Override
        protected Integer doInBackground(Void... voids) {

            return MeadicationDb.newInstance(Medication.this).
                    getMedicationDao().
                    getId(medicationTypeET.getText().toString(), Integer.valueOf(dosageET.getText().toString()));

        }

        @Override
        protected void onPostExecute(Integer integer) {

            saveMed(integer);
            super.onPostExecute(integer);

        }
    }



}
